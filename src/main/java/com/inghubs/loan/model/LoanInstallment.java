package com.inghubs.loan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_installment",
        indexes = {
                @Index(name = "idx_loan_installment_loan_id", columnList = "loan_id"),
                @Index(name = "idx_loan_installment_due_date", columnList = "due_date"),
                @Index(name = "idx_loan_installment_is_paid", columnList = "is_paid")
        })
public class LoanInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "loan_id", nullable = false)
    private Loan loan;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
