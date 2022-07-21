package com.marko.LoanCalculator.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Result")
public class ResultDto {
    @ApiModelProperty(notes = "Id of the result")
    private Integer id;
    @ApiModelProperty(notes = "Total payments")
    private Double totalPayments;
    @ApiModelProperty(notes = "Total interest")
    private Double totalInterest;
    @ApiModelProperty(notes = "Schedule")
    private List<ScheduleDto> schedule;

}
