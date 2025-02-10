package com.inghubs.loan.domain;

import java.math.BigDecimal;

public record CustomerDTO(
    Long id,
    String guid,
    String name,
    String surname,
    BigDecimal creditLimit,
    BigDecimal usedCreditLimit
) {}
