package com.inghubs.loan.rules;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.error.exception.InvalidInstallmentCountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstallmentCountValidatorTest {

    private InstallmentCountValidator installmentCountValidator;
    private LoanProperties loanProperties;

    @BeforeEach
    void setUp() {
        loanProperties = Mockito.mock(LoanProperties.class);
        installmentCountValidator = new InstallmentCountValidator(loanProperties);
    }

    @Test
    void shouldNotThrowExceptionWhenInstallmentCountIsValid() {
        Integer validInstallmentCount = 12;
        Mockito.when(loanProperties.getValidInstallmentCounts()).thenReturn(List.of(6, 9, 12, 24));

        assertDoesNotThrow(() -> installmentCountValidator.evaluate(validInstallmentCount));
    }

    @Test
    void shouldThrowExceptionWhenInstallmentCountIsInvalid() {
        Integer invalidInstallmentCount = 15;
        Mockito.when(loanProperties.getValidInstallmentCounts()).thenReturn(List.of(6, 9, 12, 24));

        InvalidInstallmentCountException exception = assertThrows(InvalidInstallmentCountException.class,
                () -> installmentCountValidator.evaluate(invalidInstallmentCount));

        assertTrue(exception.getMessage().contains("Valid values are [6, 9, 12, 24]"));
    }
}
