package com.inghubs.loan.repository;

import com.inghubs.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomerGuid(String customerGuid);
}
