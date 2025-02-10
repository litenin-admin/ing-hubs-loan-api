package com.inghubs.loan.error.exception;

public class PaymentAmountExceedsInstallmentsException extends RuntimeException {

    public PaymentAmountExceedsInstallmentsException(String message) {
        super(message);
    }

}
