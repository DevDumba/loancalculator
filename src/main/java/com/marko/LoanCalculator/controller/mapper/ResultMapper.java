package com.marko.LoanCalculator.controller.mapper;

import com.marko.LoanCalculator.controller.dto.ResultDto;
import com.marko.LoanCalculator.model.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ScheduleMapper.class})
public interface ResultMapper extends EntityMapper<ResultDto, Result> {

    @Mapping(source = "totalpayments", target = "totalPayments")
    @Mapping(source = "totalinterest", target = "totalInterest")
    ResultDto toDto(Result result);

    Result toEntity(ResultDto resultDto);
}
