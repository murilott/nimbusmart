import React, { useEffect, useState } from 'react'
import "../style/checkoutpopup.css"
import InputForm from './InputForm';
import type { TransactionCreationRequest } from '../dto/request/TransactionCreationRequest';
import { transactionCreationNew } from '../new/request/TransactionCreationRequest';
import BigNumber from 'bignumber.js';
import type { PaymentDto } from '../types/PaymentDto';
import { toBrl } from '../helper/toPrice';
import type { OrderDto } from '../types/OrderDto';
import { createTransaction, listPayments } from '../service/payment.service';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

interface CheckoutPopupProps {
    toggle: (open: boolean) => void,
    dataOrder: OrderDto,
    // setOrder: (order: OrderDto) => void;
}

const pays: PaymentDto[] = [
    {
        id: 1,
        name: "CrediTop",
        method: "CREDIT",
        funds: new BigNumber(0),
        creditLimit: new BigNumber(500),
        limitSpent: new BigNumber(0)
    },
    {
        id: 2,
        name: "DebitX",
        method: "DEBIT",
        funds: new BigNumber(200),
        creditLimit: new BigNumber(0),
        limitSpent: new BigNumber(0)
    },
    {
        id: 3,
        name: "Pix",
        method: "PIX",
        funds: new BigNumber(600),
        creditLimit: new BigNumber(0),
        limitSpent: new BigNumber(0)
    },
]



function CheckoutPopup({ toggle, dataOrder }: CheckoutPopupProps) {
    const [transaction, setTransaction] = useState<TransactionCreationRequest>(transactionCreationNew);
    const [order, setOrder] = useState<OrderDto>(dataOrder);
    const [payment, setPayment] = useState<PaymentDto>();

    const queryClient = useQueryClient();

    const handleTransactionCreation = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setTransaction((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    function togglePopup() {
        toggle(false);
    }

    useEffect(() => {
        const pay = payments?.find((p) => transaction.paymentId == p.id)
        setPayment(pay);
    }, [transaction.paymentId])

    const { mutateAsync, isPending } = useMutation({
        mutationFn: createTransaction,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['order', "deliveryTracking"] });
        },
    });

    async function buy() {
        const payload: TransactionCreationRequest = {
            ...transaction,
            orderId: order.id
        }

        // console.log(payload);
        try {
            const response = await mutateAsync(payload);
            console.log("Response created: " + response.toString());
            // setPaymentCreation({ ...paymentCreationNew });
        } catch (err: unknown) {
            console.error("Unexpected error:", err);
        }

    }

    const { data: payments } = useQuery<PaymentDto[]>({
        queryKey: ['payments'],
        queryFn: listPayments,
    });

    return (
        <div>
            <div className="overlay" onClick={togglePopup} />

            <div className="popup-card">
                <div>
                    <h3>Checkout</h3>
                </div>

                <div className='popup-card-info'>
                    <div>
                        <InputForm
                            field='Select Payment:'
                            type='text'
                            name='paymentId'
                            value={transaction.paymentId}
                            options={(payments ?? []).map((p) => ({ value: p.id, label: p.name }))}
                            onChange={handleTransactionCreation}
                        />

                        {transaction.paymentId &&
                            <div>
                                {(payment?.method.value == "DEBIT" || payment?.method.value == "PIX") &&
                                    <>
                                        <p>Funds: {toBrl(new BigNumber(payment.funds))}</p>

                                        {(new BigNumber(order.totalCost).isGreaterThan(new BigNumber(payment?.funds))) &&
                                            <p style={{ color: "red" }}>Insufficent funds</p>
                                        }
                                    </>
                                }

                                {payment?.method.value == "CREDIT" &&
                                    <>
                                        <p>Limit: {toBrl(new BigNumber(payment.creditLimit))}</p>
                                        <p>Spent: {toBrl(new BigNumber(payment.limitSpent))}</p>
                                    </>
                                }
                            </div>
                        }

                        {!payment &&
                            <p>Select a payment</p>
                        }

                        {payments?.length == 0 &&
                            <p>No payment found, create one first</p>
                        }
                    </div>

                    <p>Total: {toBrl(new BigNumber(order.totalCost))}</p>
                </div>

                {isPending ??
                    <div>
                        Loading...
                    </div>
                }

                <div className='popup-card-actions'>
                    <button onClick={togglePopup}>Close</button>
                    <button onClick={buy} disabled={
                        ((payment?.method.value == "DEBIT" ||
                            payment?.method.value == "PIX") && new BigNumber(order.totalCost).isGreaterThan(new BigNumber(payment?.funds) ?? 0))
                        || !payment
                        || isPending
                    }>Buy</button>
                </div>
            </div>
        </div>
    )
}

export default CheckoutPopup