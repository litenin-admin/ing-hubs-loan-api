package com.inghubs.loan.domain;

public record ResponsePayload<T>(T data, ErrorInfo error) {

    public ResponsePayload(T data) {
        this(data, null);
    }

}