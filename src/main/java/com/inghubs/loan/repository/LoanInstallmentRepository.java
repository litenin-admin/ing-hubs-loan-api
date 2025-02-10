package com.inghubs.loan.repository;

import com.inghubs.loan.model.LoanInstallment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

    List<LoanInstallment> findByLoanIdAndDueDateBetween(Long loanId, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    List<LoanInstallment> findByLoanId(Long loanId);

    List<LoanInstallment> findByLoanId(Long loanId, Sort sort);
}
