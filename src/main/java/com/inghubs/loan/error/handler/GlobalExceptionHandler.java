package com.inghubs.loan.error.handler;

import com.inghubs.loan.domain.ResponsePayload;
import com.inghubs.loan.error.ErrorCodes;
import com.inghubs.loan.domain.ErrorInfo;
import com.inghubs.loan.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ResponsePayload<Void>> handleLoanNotFoundException(LoanNotFoundException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.LOAN_NOT_FOUND.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientCreditLimitException.class)
    public ResponseEntity<ResponsePayload<Void>> handleInsufficientCreditLimitException(InsufficientCreditLimitException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INSUFFICIENT_CREDIT_LIMIT.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInstallmentCountException.class)
    public ResponseEntity<ResponsePayload<Void>> handleInvalidInstallmentCountException(InvalidInstallmentCountException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INVALID_INSTALLMENT_COUNT.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInterestRateException.class)
    public ResponseEntity<ResponsePayload<Void>> handleInvalidInterestRateException(InvalidInterestRateException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INVALID_INTEREST_RATE.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InstallmentsNotFoundException.class)
    public ResponseEntity<ResponsePayload<Void>> handleInstallmentsNotFoundException(InstallmentsNotFoundException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INSTALLMENTS_NOT_FOUND.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentAmountExceedsInstallmentsException.class)
    public ResponseEntity<ResponsePayload<Void>> handlePaymentAmountExceedsInstallments(PaymentAmountExceedsInstallmentsException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.PAYMENT_AMOUNT_EXCEEDS_INSTALLMENTS.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientPaymentAmountException.class)
    public ResponseEntity<ResponsePayload<Void>> handleInsufficientPaymentAmountException(InsufficientPaymentAmountException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INSUFFICIENT_PAYMENT_AMOUNT.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanAlreadyPaidException.class)
    public ResponseEntity<ResponsePayload<Void>> handleLoanAlreadyPaidException(LoanAlreadyPaidException ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.LOAN_ALREADY_PAID_EXCEPTION.getCode(), getRootCauseMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponsePayload<Void>> handleAuthorizationDeniedException(Exception ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.FORBIDDEN.getCode(), getMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePayload<Void>> handleGenericException(Exception ex) {
        var errorInfo = new ErrorInfo(ErrorCodes.INTERNAL_SERVER_ERROR.getCode(), getMessage(ex));
        return new ResponseEntity<>(getErrorPayload(errorInfo), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponsePayload<Void> getErrorPayload(ErrorInfo errorInfo) {
        return new ResponsePayload<>(null, errorInfo);
    }

}
