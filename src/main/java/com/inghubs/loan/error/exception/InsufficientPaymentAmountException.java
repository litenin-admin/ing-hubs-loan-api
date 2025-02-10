package com.inghubs.loan.error.exception;

public class InsufficientPaymentAmountException extends RuntimeException {

    public InsufficientPaymentAmountException(String message) {
        super(message);
    }

}
