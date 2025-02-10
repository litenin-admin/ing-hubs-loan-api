package com.inghubs.loan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan",
        indexes = {
                @Index(name = "idx_loan_is_paid", columnList = "is_paid"),
                @Index(name = "idx_loan_customer_guid", columnList = "customer_guid")
        })
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "number_of_installments")
    private int numberOfInstallments;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "customer_guid")
    private String customerGuid;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanInstallment> installments;
}
