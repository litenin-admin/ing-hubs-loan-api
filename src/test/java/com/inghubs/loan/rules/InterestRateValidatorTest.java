package com.inghubs.loan.rules;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.error.exception.InvalidInterestRateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InterestRateValidatorTest {

    private InterestRateValidator interestRateValidator;
    private LoanProperties loanProperties;

    @BeforeEach
    void setUp() {
        loanProperties = Mockito.mock(LoanProperties.class);
        interestRateValidator = new InterestRateValidator(loanProperties);
    }

    @Test
    void shouldNotThrowExceptionWhenInterestRateIsWithinValidRange() {
        BigDecimal validInterestRate = new BigDecimal("5.0");
        Mockito.when(loanProperties.getMinInterestRate()).thenReturn(new BigDecimal("2.0"));
        Mockito.when(loanProperties.getMaxInterestRate()).thenReturn(new BigDecimal("10.0"));

        assertDoesNotThrow(() -> interestRateValidator.evaluate(validInterestRate));
    }

    @Test
    void shouldThrowExceptionWhenInterestRateIsBelowMin() {
        BigDecimal invalidInterestRate = new BigDecimal("1.0");
        Mockito.when(loanProperties.getMinInterestRate()).thenReturn(new BigDecimal("2.0"));
        Mockito.when(loanProperties.getMaxInterestRate()).thenReturn(new BigDecimal("10.0"));

        InvalidInterestRateException exception = assertThrows(InvalidInterestRateException.class,
                () -> interestRateValidator.evaluate(invalidInterestRate));

        assertEquals("Interest rate must be between 2.0 and 10.0", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInterestRateIsAboveMax() {
        BigDecimal invalidInterestRate = new BigDecimal("12.0");
        Mockito.when(loanProperties.getMinInterestRate()).thenReturn(new BigDecimal("2.0"));
        Mockito.when(loanProperties.getMaxInterestRate()).thenReturn(new BigDecimal("10.0"));

        InvalidInterestRateException exception = assertThrows(InvalidInterestRateException.class,
                () -> interestRateValidator.evaluate(invalidInterestRate));

        assertEquals("Interest rate must be between 2.0 and 10.0", exception.getMessage());
    }
}
