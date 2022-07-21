package com.marko.LoanCalculator.controller.mapper;

import com.marko.LoanCalculator.controller.dto.ScheduleDto;
import com.marko.LoanCalculator.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper extends EntityMapper<ScheduleDto, Schedule> {

    @Mapping(source = "paymentamount", target = "paymentAmount")
    @Mapping(source = "principalamount", target = "principalAmount")
    @Mapping(source = "interestamount", target = "interestAmount")
    @Mapping(source = "balanceowed", target = "balanceOwed")
    ScheduleDto toDto(Schedule entity);

    Schedule toEntity(ScheduleDto dto);
}
