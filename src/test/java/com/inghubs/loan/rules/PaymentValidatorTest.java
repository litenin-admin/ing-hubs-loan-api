package com.inghubs.loan.rules;

import com.inghubs.loan.error.ValidationMessages;
import com.inghubs.loan.error.exception.InsufficientPaymentAmountException;
import com.inghubs.loan.error.exception.PaymentAmountExceedsInstallmentsException;
import com.inghubs.loan.model.LoanInstallment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentValidatorTest {

    private PaymentValidator paymentValidator;

    @BeforeEach
    void setUp() {
        paymentValidator = new PaymentValidator();
    }

    @Test
    void testValidatePaymentAmount_WhenPaymentIsTooLow() {
        LoanInstallment installment1 = new LoanInstallment(null, BigDecimal.valueOf(100), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        LoanInstallment installment2 = new LoanInstallment(null, BigDecimal.valueOf(150), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        BigDecimal paymentAmount = BigDecimal.valueOf(50);

        InsufficientPaymentAmountException exception = assertThrows(InsufficientPaymentAmountException.class, () ->
                paymentValidator.evaluate(List.of(installment1, installment2), paymentAmount));

        assertEquals(ValidationMessages.PAYMENT_TOO_LOW, exception.getMessage());
    }

    @Test
    void testValidatePaymentAmount_WhenPaymentExceedsInstallments() {
        LoanInstallment installment1 = new LoanInstallment(null, BigDecimal.valueOf(100), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        LoanInstallment installment2 = new LoanInstallment(null, BigDecimal.valueOf(150), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        BigDecimal paymentAmount = BigDecimal.valueOf(300);

        PaymentAmountExceedsInstallmentsException exception = assertThrows(PaymentAmountExceedsInstallmentsException.class, () ->
                paymentValidator.evaluate(List.of(installment1, installment2), paymentAmount));

        assertEquals(ValidationMessages.PAYMENT_EXCEEDS_TOTAL, exception.getMessage());
    }

    @Test
    void testValidatePaymentAmount_WhenPaymentDoesNotMatchInstallments() {
        LoanInstallment installment1 = new LoanInstallment(null, BigDecimal.valueOf(100), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        LoanInstallment installment2 = new LoanInstallment(null, BigDecimal.valueOf(150), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        BigDecimal paymentAmount = BigDecimal.valueOf(200);

        InsufficientPaymentAmountException exception = assertThrows(InsufficientPaymentAmountException.class, () ->
                paymentValidator.evaluate(List.of(installment1, installment2), paymentAmount));

        assertEquals(ValidationMessages.PAYMENT_NOT_MATCHING_INSTALLMENTS, exception.getMessage());
    }

    @Test
    void testValidatePaymentAmount_WhenPaymentIsValid() {
        LoanInstallment installment1 = new LoanInstallment(null, BigDecimal.valueOf(100), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        LoanInstallment installment2 = new LoanInstallment(null, BigDecimal.valueOf(150), BigDecimal.ZERO, LocalDateTime.now(), null, false, null);
        BigDecimal paymentAmount = BigDecimal.valueOf(250);

        assertDoesNotThrow(() ->
                paymentValidator.evaluate(List.of(installment1, installment2), paymentAmount));
    }

}
