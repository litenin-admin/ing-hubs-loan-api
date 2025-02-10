package com.inghubs.loan.mappers;

import com.inghubs.loan.domain.LoanDto;
import com.inghubs.loan.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    @Mapping(source = "paid", target = "isPaid")
    LoanDto toDto(Loan loan);

    List<LoanDto> toDtoList(List<Loan> loans);
}
