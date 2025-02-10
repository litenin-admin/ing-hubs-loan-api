package com.inghubs.loan.service;

import com.inghubs.loan.domain.LoanRequestDto;
import com.inghubs.loan.error.exception.LoanNotFoundException;
import com.inghubs.loan.model.Customer;
import com.inghubs.loan.model.Loan;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.repository.LoanRepository;
import com.inghubs.loan.repository.LoanInstallmentRepository;
import com.inghubs.loan.rules.CreditLimitValidator;
import com.inghubs.loan.rules.InstallmentCountValidator;
import com.inghubs.loan.rules.InterestRateValidator;
import com.inghubs.loan.service.strategy.LoanCalculationStrategy;
import com.inghubs.loan.utils.Dates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanManagementServiceTest {

    @InjectMocks
    private LoanManagementService loanManagementService;

    @Mock
    private CreditLimitValidator creditLimitValidator;
    @Mock
    private InterestRateValidator interestRateValidator;
    @Mock
    private InstallmentCountValidator installmentCountValidator;
    @Mock
    private LoanCalculationStrategy calculationStrategy;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    private Customer customer;
    private LoanRequestDto loanRequestDto;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setGuid("tester1@inghubs.com");
        customer.setUsedCreditLimit(BigDecimal.ZERO);
        customer.setCreditLimit(BigDecimal.valueOf(5000));

        loanRequestDto = new LoanRequestDto(
                "tester1@inghubs.com",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(12),
                5);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createLoan_ShouldReturnLoan_WhenRequestIsValid() {
        BigDecimal totalAmount = BigDecimal.valueOf(1050);
        Loan loan = new Loan();
        loan.setCustomerGuid("tester1@inghubs.com");
        loan.setLoanAmount(loanRequestDto.amount());
        loan.setNumberOfInstallments(loanRequestDto.numberOfInstallments());
        loan.setCreateDate(Dates.now());
        loan.setPaid(false);

        when(calculationStrategy.calculateTotalAmount(loanRequestDto.amount(), loanRequestDto.interestRate()))
                .thenReturn(totalAmount);
        when(customerService.findByGuid(loanRequestDto.customerGuid()))
                .thenReturn(customer);

        doNothing().when(creditLimitValidator).evaluate(customer, totalAmount);
        doNothing().when(interestRateValidator).evaluate(loanRequestDto.interestRate());
        doNothing().when(installmentCountValidator).evaluate(loanRequestDto.numberOfInstallments());

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan createdLoan = loanManagementService.createLoan(loanRequestDto);

        assertNotNull(createdLoan);
        assertEquals(loanRequestDto.amount(), createdLoan.getLoanAmount());
        assertEquals(loanRequestDto.numberOfInstallments(), createdLoan.getNumberOfInstallments());
    }

    @Test
    void getLoanById_ShouldReturnLoan_WhenLoanExists() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setCustomerGuid("tester1@inghubs.com");
        loan.setLoanAmount(BigDecimal.valueOf(1000));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Loan fetchedLoan = loanManagementService.getLoanById(1L);

        assertNotNull(fetchedLoan);
        assertEquals(1L, fetchedLoan.getId());
    }

    @Test
    void getLoanById_ShouldThrowLoanNotFoundException_WhenLoanDoesNotExist() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class,
                () -> loanManagementService.getLoanById(1L));

        assertEquals("Loan not found with id 1", exception.getMessage());
    }

    @Test
    void listInstallments_ShouldReturnInstallments_WhenLoanExists() {
        LoanInstallment installment = new LoanInstallment();
        installment.setId(1L);
        installment.setAmount(BigDecimal.valueOf(200));
        installment.setPaid(false);

        when(loanInstallmentRepository.findByLoanId(1L)).thenReturn(List.of(installment));

        List<LoanInstallment> installments = loanManagementService.listInstallments(1L);

        assertNotNull(installments);
        assertEquals(1, installments.size());
        assertEquals(BigDecimal.valueOf(200), installments.getFirst().getAmount());
    }

}