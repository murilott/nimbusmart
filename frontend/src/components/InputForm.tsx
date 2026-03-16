import React, { type Ref } from 'react'
import "../style/inputform.css"

interface InputFormProps<T> {
    field: string;
    name: string;
    value: T;
    type: string;
    classStyle?: string;
    options?: { label: string | number | null; value: T }[];
    onChange?: (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
    ) => void;
    onFocus?: (
        event: React.FocusEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
    ) => void;
    onBlur?: (
        event: React.FocusEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
    ) => void;
    ref?: Ref<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement> | null;
    disabled?: boolean;
    readOnly?: boolean;
}

function InputForm<T>({ 
    field, name, value, type, classStyle,
    onChange, onFocus, onBlur,
    ref, disabled, readOnly, options, 
}: InputFormProps<T>) {
    return (
        <div className={`${classStyle} input-form-div`}>
            <label htmlFor={name}>{field}</label>

            {options ? (
                <select
                    name={name}
                    value={
                        value === null || value === undefined ? ""
                            : typeof value === "object"
                                ? JSON.stringify(value)
                                : String(value)
                    }
                    disabled={disabled}
                    onChange={onChange}
                    onFocus={onFocus}
                    onBlur={onBlur}
                    ref={ref as Ref<HTMLSelectElement>}
                    className='input-form-option'
                >
                    <option value="">None</option>

                    {options.map((op, idx) => (
                        <option
                            key={idx}
                            value={
                                typeof op.value === "object"
                                    ? JSON.stringify(op.value)
                                    : String(op.value)
                            }
                        >
                            {op.label}
                        </option>
                    ))}
                </select>
            ) : type === "textarea" ? (
                <textarea
                    name={name}
                    value={String(value)}
                    readOnly={readOnly}
                    disabled={disabled}
                    onChange={onChange}
                    onFocus={onFocus}
                    onBlur={onBlur}
                    ref={ref as Ref<HTMLTextAreaElement>}
                />
            ) : type === "checkbox" ? (
                <input
                    type="checkbox"
                    name={name}
                    checked={Boolean(value)}
                    disabled={disabled}
                    onChange={onChange}
                    onFocus={onFocus}
                    onBlur={onBlur}
                    ref={ref as Ref<HTMLInputElement>}
                />
            ) : (
                <input
                    type={type}
                    name={name}
                    value={value as string | number | readonly string[] | undefined}
                    readOnly={readOnly}
                    disabled={disabled}
                    onChange={onChange}
                    onFocus={onFocus}
                    onBlur={onBlur}
                    ref={ref as Ref<HTMLInputElement>}
                    className='input-form-inp'
                />
            )}
        </div>
    )
}

export default InputForm