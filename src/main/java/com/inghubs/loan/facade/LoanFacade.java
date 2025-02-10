package com.inghubs.loan.facade;

import com.inghubs.loan.domain.*;
import com.inghubs.loan.mappers.LoanInstallmentsMapper;
import com.inghubs.loan.mappers.LoanMapper;
import com.inghubs.loan.model.Loan;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.service.LoanManagementService;
import com.inghubs.loan.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanFacade {

    private final PaymentService paymentService;
    private final LoanManagementService loanManagementService;

    public LoanDto createLoan(LoanRequestDto source) {
        Loan loan = loanManagementService.createLoan(source);
        return LoanMapper.INSTANCE.toDto(loan);
    }

    public PaymentDetailDto payLoan(PaymentRequestDto request) {
        return paymentService.payLoan(request);
    }

    public LoanDto getLoanById(Long loanId) {
        Loan loan = loanManagementService.getLoanById(loanId);
        return LoanMapper.INSTANCE.toDto(loan);
    }

    public List<LoanDto> listLoans(String customerGuid, Boolean isPaid, Integer numberOfInstallments) {
        List<Loan> loans = loanManagementService.listLoans(customerGuid, isPaid, numberOfInstallments);
        return LoanMapper.INSTANCE.toDtoList(loans);
    }

    public List<LoanInstallmentDto> listInstallments(Long loanId) {
        List<LoanInstallment> installments = loanManagementService.listInstallments(loanId);
        return LoanInstallmentsMapper.INSTANCE.toDtoList(installments);
    }

}
