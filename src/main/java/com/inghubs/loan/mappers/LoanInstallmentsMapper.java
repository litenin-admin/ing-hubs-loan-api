package com.inghubs.loan.mappers;

import com.inghubs.loan.domain.LoanInstallmentDto;
import com.inghubs.loan.model.LoanInstallment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanInstallmentsMapper {

    LoanInstallmentsMapper INSTANCE = Mappers.getMapper(LoanInstallmentsMapper.class);

    @Mapping(source = "paid", target = "isPaid")
    LoanInstallmentDto toDto(LoanInstallment loanInstallment);

    List<LoanInstallmentDto> toDtoList(List<LoanInstallment> loans);
}
