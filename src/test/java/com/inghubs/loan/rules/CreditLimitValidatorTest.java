package com.inghubs.loan.rules;

import com.inghubs.loan.error.ValidationMessages;
import com.inghubs.loan.error.exception.InsufficientCreditLimitException;
import com.inghubs.loan.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditLimitValidatorTest {

    private CreditLimitValidator creditLimitValidator;
    private Customer customer;

    @BeforeEach
    void setUp() {
        creditLimitValidator = new CreditLimitValidator();
        customer = Mockito.mock(Customer.class);
    }

    @Test
    void shouldNotThrowExceptionWhenCreditLimitIsSufficient() {
        BigDecimal totalAmount = new BigDecimal("1000");
        Mockito.when(customer.getUsedCreditLimit()).thenReturn(new BigDecimal("500"));
        Mockito.when(customer.getCreditLimit()).thenReturn(new BigDecimal("2000"));

        assertDoesNotThrow(() -> creditLimitValidator.evaluate(customer, totalAmount));
    }

    @Test
    void shouldThrowExceptionWhenCreditLimitIsExceeded() {
        BigDecimal totalAmount = new BigDecimal("1500");
        Mockito.when(customer.getUsedCreditLimit()).thenReturn(new BigDecimal("1200"));
        Mockito.when(customer.getCreditLimit()).thenReturn(new BigDecimal("2000"));

        InsufficientCreditLimitException exception = assertThrows(InsufficientCreditLimitException.class,
                () -> creditLimitValidator.evaluate(customer, totalAmount));

        assertEquals(ValidationMessages.INSUFFICIENT_CREDIT_LIMIT, exception.getMessage());
    }

}
