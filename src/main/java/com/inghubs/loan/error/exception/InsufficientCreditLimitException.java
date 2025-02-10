package com.inghubs.loan.error.exception;

public class InsufficientCreditLimitException extends RuntimeException {

    public InsufficientCreditLimitException(String message) {
        super(message);
    }
}
