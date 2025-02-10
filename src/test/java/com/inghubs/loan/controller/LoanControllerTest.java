package com.inghubs.loan.controller;

import com.inghubs.loan.domain.*;
import com.inghubs.loan.facade.LoanFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoanFacade loanFacade;

    @InjectMocks
    private LoanController loanController;

    private ObjectMapper objectMapper;

    private LoanRequestDto loanRequestDto;
    private PaymentRequestDto paymentRequestDto;
    private LoanDto loanDto;
    private PaymentDetailDto paymentDetailDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
        objectMapper = new ObjectMapper();

        loanRequestDto = new LoanRequestDto("tester1@inghubs.com", BigDecimal.valueOf(3000), BigDecimal.valueOf(0.2), 6);
        paymentRequestDto = new PaymentRequestDto("tester1@inghubs.com", BigDecimal.valueOf(500), 1L);

        loanDto = new LoanDto(1L, "tester1@inghubs.com", BigDecimal.valueOf(3000), 6, false, List.of());
        paymentDetailDto = new PaymentDetailDto("Payment processed", List.of());
    }

    @Test
    void testCreateLoan() throws Exception {
        when(loanFacade.createLoan(any(LoanRequestDto.class))).thenReturn(loanDto);

        mockMvc.perform(post("/api/v1/loans/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(loanDto.id()))
                .andExpect(jsonPath("$.data.loanAmount").value(loanDto.loanAmount().toString()))
                .andExpect(jsonPath("$.data.numberOfInstallments").value(loanDto.numberOfInstallments()));
    }

    @Test
    void testPayLoan() throws Exception {
        when(loanFacade.payLoan(any(PaymentRequestDto.class))).thenReturn(paymentDetailDto);

        mockMvc.perform(post("/api/v1/loans/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").value(paymentDetailDto.description()))
                .andExpect(jsonPath("$.data.installments").isArray());
    }

    @Test
    void testGetLoanById() throws Exception {
        when(loanFacade.getLoanById(anyLong())).thenReturn(loanDto);

        mockMvc.perform(get("/api/v1/loans/{loanId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(loanDto.id()))
                .andExpect(jsonPath("$.data.loanAmount").value(loanDto.loanAmount().toString()))
                .andExpect(jsonPath("$.data.numberOfInstallments").value(loanDto.numberOfInstallments()));
    }

    @Test
    void testListInstallments() throws Exception {
        LoanInstallmentDto installmentDto = new LoanInstallmentDto(1L, BigDecimal.valueOf(500), BigDecimal.ZERO, null, false);
        List<LoanInstallmentDto> installments = List.of(installmentDto);
        when(loanFacade.listInstallments(anyLong())).thenReturn(installments);

        mockMvc.perform(get("/api/v1/loans/{loanId}/installments", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(installmentDto.id()))
                .andExpect(jsonPath("$.data[0].amount").value(installmentDto.amount().toString()));
    }

}
