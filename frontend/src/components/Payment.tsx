import React, { useState } from 'react'
import { paymentNew } from '../new/PaymentDto'
import type { PaymentDto } from '../types/PaymentDto';
import type { PaymentCreationRequest } from '../dto/request/PaymentCreationRequest';
import { paymentCreationNew } from '../new/request/PaymentCreationRequest';
import BigNumber from 'bignumber.js';
import InputForm from './InputForm';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { createPayment, listPayments } from '../service/payment.service';

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

function Payment() {
    const [paymentCreation, setPaymentCreation] = useState<PaymentCreationRequest>(paymentCreationNew);
    const [selectedPayment, setSelectedPayment] = useState<PaymentDto>(paymentNew);

    const [editPayment, setEditPayment] = useState<PaymentCreationRequest>(paymentCreationNew);

    const [bNewPayment, setBNewPayment] = useState<boolean>(false);
    const [bEditPayment, setBEditPayment] = useState<boolean>(false);

    const queryClient = useQueryClient();

    function changeView(opt: string) {
        if (opt === "a") {
            setBNewPayment(true);
            setBEditPayment(false);
        }
        if (opt === "b") {
            setBNewPayment(false);
            setBEditPayment(true);
        }
    }

    const handlePaymentCreation = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setPaymentCreation((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handlePaymentEdit = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setEditPayment((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const { mutateAsync, isPending } = useMutation({
        mutationFn: createPayment,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['payments'] });
        },
    });


    async function creatingPayment() {
        const payload = { ...paymentCreation };

        try {
            const response = await mutateAsync(payload);
            console.log("Response created: " + response.toString());
            setPaymentCreation({ ...paymentCreationNew });
        } catch (err: unknown) {
            console.error("Unexpected error:", err);
        }
    }

    function editingPayment() {
        const payload = { ...editPayment };

        console.log(payload);
    }

    function selectPayment(payment: PaymentDto) {
        setSelectedPayment(payment);
        setEditPayment({ name: payment.name, method: payment.method, creditLimit: payment.creditLimit, funds: payment.funds });
        console.log(payment);
    }

    const { isLoading, error, data: payments } = useQuery<PaymentDto[]>({
        queryKey: ['payments'],
        queryFn: listPayments,
    });

    return (
        <div className='content'>
            <h3>Payment</h3>

            <p>Manage and create payment methods.</p>

            <div className='card card-body table-wrapper'>
                {((payments ?? []).length == 0 && !isLoading && !error) ?
                    <p>No Payments found.</p>
                    :
                    <table className='products-table'>
                        <colgroup>
                            <col style={{ width: '50px' }} />
                            <col style={{ width: '110px' }} />
                        </colgroup>
                        <thead>
                            <tr style={{ textAlign: 'left', borderBottom: '2px solid #ddd' }}>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Method</th>
                                <th>Funds (R$)</th>
                                <th>Credit Limit (R$)</th>
                                <th>Limit Spent (R$)</th>
                            </tr>
                        </thead>
                        <tbody>
                            {payments?.map((payment) => (
                                <tr
                                    key={payment.id}
                                    className={`${payment.id == selectedPayment?.id ? 'products-table-selected' : ''}`}
                                    onClick={() => selectPayment(payment)}
                                >
                                    <td>{payment.id}</td>
                                    <td>{payment.name}</td>
                                    <td>{payment.method.value.charAt(0).toUpperCase() + payment.method.value.slice(1).toLowerCase()}</td>
                                    <td>{payment.funds.toString()}</td>
                                    <td>{payment.creditLimit.toString()}</td>
                                    <td>{payment.limitSpent.toString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                }
            </div>

            <div className='actions-bar'>
                <button onClick={() => changeView("a")} >New Payment</button>
                {/* <button onClick={() => changeView("b")} disabled={selectedPayment == null}>Edit Payment</button>
                <button onClick={() => changeView("c")}>Delete Payment</button> */}
            </div>

            <div className={`actions-info card ${bEditPayment ? 'editing' : ''}`}>
                {bNewPayment &&
                    <div>
                        <h4>New Payment</h4>

                        <InputForm
                            field='Name:'
                            type='text'
                            name='name'
                            value={paymentCreation.name}
                            onChange={handlePaymentCreation}
                        />

                        <InputForm
                            field='Method:'
                            type='text'
                            name='method'
                            options={[
                                { label: "Pix", value: "PIX" },
                                { label: "Debit", value: "DEBIT" },
                                { label: "Credit", value: "CREDIT" },
                            ]}
                            value={paymentCreation.method}
                            onChange={handlePaymentCreation}
                        />

                        <InputForm
                            field='Funds:'
                            type='number'
                            name='funds'
                            value={paymentCreation.funds}
                            onChange={handlePaymentCreation}
                            disabled={(paymentCreation.method != "DEBIT")
                                && paymentCreation.method != "PIX"
                            }
                        />

                        <InputForm
                            field='Credit Limit:'
                            type='number'
                            name='creditLimit'
                            value={paymentCreation.creditLimit}
                            onChange={handlePaymentCreation}
                            disabled={paymentCreation.method != "CREDIT"}
                        />

                        <button onClick={creatingPayment} disabled={paymentCreation.name.trim() == "" || !paymentCreation.method}>Add payment</button>
                    </div>
                }

                {/* {bEditPayment &&
                    <div>
                        <h4>Editing {selectedPayment?.name}</h4>

                        <InputForm
                            field='Name:'
                            type='text'
                            name='name'
                            value={editPayment.name}
                            onChange={handlePaymentEdit}
                        />

                        <InputForm
                            field='Method:'
                            type='text'
                            name='method'
                            options={[
                                { label: "Pix", value: "PIX" },
                                { label: "Debit", value: "DEBIT" },
                                { label: "Credit", value: "CREDIT" },
                            ]}
                            value={editPayment.method}
                            onChange={handlePaymentEdit}
                        />

                        <InputForm
                            field='Funds:'
                            type='number'
                            name='funds'
                            value={editPayment.funds}
                            onChange={handlePaymentEdit}
                            disabled={(paymentCreation.method != "DEBIT")
                                && paymentCreation.method != "PIX"
                            }
                        />

                        <InputForm
                            field='Credit Limit:'
                            type='number'
                            name='creditLimit'
                            value={editPayment.creditLimit}
                            onChange={handlePaymentEdit}
                            disabled={paymentCreation.method != "CREDIT"}
                        />

                        <button onClick={editingPayment}>Edit item</button>
                    </div>
                } */}

                {!bNewPayment &&
                    <div>
                        <h4>Select a payment or create one</h4>
                    </div>
                }

            </div>
        </div>
    )
}

export default Payment