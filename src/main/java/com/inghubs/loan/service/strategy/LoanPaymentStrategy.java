package com.inghubs.loan.service.strategy;

import com.inghubs.loan.model.LoanInstallment;

public interface LoanPaymentStrategy {

    void processPayment(LoanInstallment installment);
}
