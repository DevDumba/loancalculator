package com.marko.LoanCalculator.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Schedule")
public class ScheduleDto {
    @ApiModelProperty(notes = "Id of the schedule")
    private Integer id;
    @ApiModelProperty(notes = "Number of the result")
    private Integer number;
    @ApiModelProperty(notes = "Payment amount")
    private Double paymentAmount;
    @ApiModelProperty(notes = "Principal amount")
    private Double principalAmount;
    @ApiModelProperty(notes = "Interest amount")
    private Double interestAmount;
    @ApiModelProperty(notes = "Balance owed")
    private Double balanceOwed;
}
