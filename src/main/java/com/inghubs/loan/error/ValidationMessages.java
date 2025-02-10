package com.inghubs.loan.error;

public class ValidationMessages {
    public static final String PAYMENT_TOO_LOW = "Payment amount is too low to pay any installments.";
    public static final String PAYMENT_EXCEEDS_TOTAL = "Payment amount cannot be greater than the total due for the installments.";
    public static final String PAYMENT_NOT_MATCHING_INSTALLMENTS = "Payment amount must be enough to fully pay installments, and it must match the exact total amount for one or more installments.";
    public static final String INSUFFICIENT_CREDIT_LIMIT = "Customer does not have enough credit limit.";
}
