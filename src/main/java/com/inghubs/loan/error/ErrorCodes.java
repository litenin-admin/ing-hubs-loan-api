package com.inghubs.loan.error;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    FORBIDDEN(1000, "Loan not found with the given ID"),
    LOAN_NOT_FOUND(1001, "Loan not found with the given ID"),
    INSTALLMENTS_NOT_FOUND(1002, "Installments not found with the given Loan ID"),
    CUSTOMER_NOT_FOUND(1003, "Customer not found with given ID"),
    INSUFFICIENT_CREDIT_LIMIT(1004, "Customer does not have enough credit limit"),
    INVALID_INSTALLMENT_COUNT(1005, "Invalid number of installments. Valid values are 6, 9, 12, or 24."),
    INVALID_INTEREST_RATE(1006, "Interest rate must be between 0.1 and 0.5"),
    PAYMENT_AMOUNT_EXCEEDS_INSTALLMENTS(1007, "Payment amount cannot be greater than the total due for the last 3 months' installments"),
    LOAN_ALREADY_PAID_EXCEPTION(1008, "Loan has already been fully paid. No further payments are allowed."),
    INSUFFICIENT_PAYMENT_AMOUNT(1009, "Payment amount is too low to pay any installments."),  // New error code for InsufficientPaymentAmountException
    INTERNAL_SERVER_ERROR(5000, "An unexpected error occurred");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
