package com.inghubs.loan.service;

import com.inghubs.loan.domain.PaymentRequestDto;
import com.inghubs.loan.error.exception.LoanAlreadyPaidException;
import com.inghubs.loan.error.exception.LoanNotFoundException;
import com.inghubs.loan.model.Loan;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.repository.LoanRepository;
import com.inghubs.loan.rules.PaymentValidator;
import com.inghubs.loan.service.strategy.EarlyPaymentStrategy;
import com.inghubs.loan.service.strategy.LatePaymentStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private PaymentValidator paymentValidator;

    @Mock
    private EarlyPaymentStrategy earlyPaymentStrategy;

    @Mock
    private LatePaymentStrategy latePaymentStrategy;

    @Spy
    @InjectMocks
    private PaymentService paymentService;

    private Loan loan;
    private List<LoanInstallment> installments;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        loan = new Loan();
        loan.setId(1L);
        loan.setCustomerGuid("tester1@inghubs.com");
        loan.setLoanAmount(BigDecimal.valueOf(12000));
        loan.setPaid(false);
        loan.setNumberOfInstallments(6);

        installments = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setId((long) (i + 1));
            installment.setAmount(BigDecimal.valueOf(2000));
            installment.setPaidAmount(BigDecimal.ZERO);
            installment.setDueDate(LocalDateTime.now().plusDays(i + 1));
            installment.setPaid(false);
            installment.setLoan(loan);
            installments.add(installment);
        }

        loan.setInstallments(installments);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testPayLoan_LoanAlreadyPaidException() {
        loan.setPaid(true);

        PaymentRequestDto paymentRequest = new PaymentRequestDto("tester1@inghubs.com", BigDecimal.valueOf(12000), 1L);

        when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));

        LoanAlreadyPaidException exception = assertThrows(LoanAlreadyPaidException.class, () -> {
            paymentService.payLoan(paymentRequest);
        });
        assertEquals("Loan ID: 1 has already been fully paid. No further payments are needed.", exception.getMessage());
    }

    @Test
    void testPayLoan_LoanNotFoundException() {
        PaymentRequestDto paymentRequest = new PaymentRequestDto("tester1@inghubs.com", BigDecimal.valueOf(12000), 1L);

        when(loanRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            paymentService.payLoan(paymentRequest);
        });
        assertEquals("Loan not found with id 1", exception.getMessage());
    }

}
