package com.inghubs.loan.service.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculationStrategyTest {

    private LoanCalculationStrategy defaultLoanCalculationStrategy;

    @BeforeEach
    void setUp() {
        defaultLoanCalculationStrategy = new LoanCalculationStrategy();
    }

    @Test
    void testCalculateTotalAmount_Success() {
        BigDecimal loanAmount = new BigDecimal("1000");
        BigDecimal interestRate = new BigDecimal("0.05");

        BigDecimal totalAmount = defaultLoanCalculationStrategy.calculateTotalAmount(loanAmount, interestRate);

        assertEquals(new BigDecimal("1050.00"), totalAmount);
    }

    @Test
    void testCalculateTotalAmount_NegativeInterestRate() {
        BigDecimal loanAmount = new BigDecimal("1000");
        BigDecimal interestRate = new BigDecimal("-0.05");

        BigDecimal totalAmount = defaultLoanCalculationStrategy.calculateTotalAmount(loanAmount, interestRate);

        assertEquals(new BigDecimal("950.00"), totalAmount);
    }

    @Test
    void testCalculateTotalAmount_LargeAmount() {
        BigDecimal loanAmount = new BigDecimal("1000000");
        BigDecimal interestRate = new BigDecimal("0.10");

        BigDecimal totalAmount = defaultLoanCalculationStrategy.calculateTotalAmount(loanAmount, interestRate);

        assertEquals(new BigDecimal("1100000.00"), totalAmount);
    }

    @Test
    void testCalculateInstallmentAmount_Success() {
        BigDecimal totalAmount = new BigDecimal("1050.00");
        int numberOfInstallments = 12;

        BigDecimal installmentAmount = defaultLoanCalculationStrategy.calculateInstallmentAmount(totalAmount, numberOfInstallments);

        assertEquals(new BigDecimal("87.50"), installmentAmount);
    }

    @Test
    void testCalculateInstallmentAmount_OneInstallment() {
        BigDecimal totalAmount = new BigDecimal("1000");
        int numberOfInstallments = 1;

        BigDecimal installmentAmount = defaultLoanCalculationStrategy.calculateInstallmentAmount(totalAmount, numberOfInstallments);

        assertEquals(new BigDecimal("1000"), installmentAmount);
    }

    @Test
    void testCalculateInstallmentAmount_MultipleInstallments() {
        BigDecimal totalAmount = new BigDecimal("1000.00");
        int numberOfInstallments = 6;

        BigDecimal installmentAmount = defaultLoanCalculationStrategy.calculateInstallmentAmount(totalAmount, numberOfInstallments);

        assertEquals(new BigDecimal("166.67").setScale(2, RoundingMode.HALF_UP), installmentAmount);
    }

    @Test
    void testCalculateInstallmentAmount_LargeInstallmentCount() {
        BigDecimal totalAmount = new BigDecimal("1000");
        int numberOfInstallments = 100;

        BigDecimal installmentAmount = defaultLoanCalculationStrategy.calculateInstallmentAmount(totalAmount, numberOfInstallments);

        assertEquals(new BigDecimal("10"), installmentAmount);
    }

    @Test
    void testCalculateInstallmentAmount_ZeroInstallments() {
        BigDecimal totalAmount = new BigDecimal("1000");
        int numberOfInstallments = 0;

        assertThrows(ArithmeticException.class, () ->
                defaultLoanCalculationStrategy.calculateInstallmentAmount(totalAmount, numberOfInstallments));
    }
}
