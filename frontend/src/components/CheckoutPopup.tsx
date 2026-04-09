import React, { useEffect, useState } from 'react'
import "../style/checkoutpopup.css"
import InputForm from './InputForm';
import type { TransactionCreationRequest } from '../dto/request/TransactionCreationRequest';
import { transactionCreationNew } from '../new/request/TransactionCreationRequest';
import BigNumber from 'bignumber.js';
import type { PaymentDto } from '../types/PaymentDto';
import { toBrl } from '../helper/toPrice';
import type { OrderDto } from '../types/OrderDto';

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
        const pay = pays.find((p) => transaction.paymentId == p.id)
        setPayment(pay);
    }, [transaction.paymentId])

    function buy() {
        const payload: TransactionCreationRequest = {
            ...transaction,
            orderId: order.id
        }

        console.log(payload);
        
    }

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
                            options={(pays ?? []).map((p) => ({ value: p.id, label: p.name }))}
                            onChange={handleTransactionCreation}
                        />

                        {transaction.paymentId &&
                            <div>
                                {(payment?.method.value == "DEBIT" || payment?.method.value == "PIX") &&
                                    <>
                                        <p>Funds: {toBrl(payment.funds)}</p>

                                        {(order.totalCost.isGreaterThan(payment?.funds)) &&
                                            <p style={{ color: "red"}}>Insufficent funds</p>
                                        }
                                    </>
                                }

                                {payment?.method.value == "CREDIT" &&
                                    <>
                                        <p>Limit: {toBrl(payment.creditLimit)}</p>
                                        <p>Spent: {toBrl(payment.limitSpent)}</p>
                                    </>
                                }
                            </div>
                        }

                        {!payment &&
                            <p>Select a payment</p>
                        }

                        {pays.length == 0 &&
                            <p>No payment found, create one first</p>
                        }
                    </div>

                    <p>Total: {toBrl(new BigNumber(order.totalCost))}</p>
                </div>

                <div className='popup-card-actions'>
                    <button onClick={togglePopup}>Close</button>
                    <button onClick={buy} disabled={
                        ((payment?.method.value == "DEBIT" ||
                        payment?.method.value == "PIX") && order.totalCost.isGreaterThan(payment?.funds ?? 0))
                        || !payment
                    }>Buy</button>
                </div>
            </div>
        </div>
    )
}

export default CheckoutPopup