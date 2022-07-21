package com.marko.LoanCalculator.controller.mapper;

import com.marko.LoanCalculator.controller.dto.RequestDto;
import com.marko.LoanCalculator.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {ResultMapper.class})
public interface RequestMapper extends EntityMapper<RequestDto, Request> {

    @Mapping(source = "loanamount", target = "loanAmount")
    @Mapping(source = "interestrate", target = "interestRate")
    @Mapping(source = "numofpayments", target = "numOfPayments")
    @Mapping(source = "paymentfreq", target = "paymentFreq")
    RequestDto toDto(Request request);

    Request toEntity(RequestDto requestDto);
}
