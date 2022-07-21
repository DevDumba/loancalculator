package com.marko.LoanCalculator.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Request")
public class RequestDto {
    @ApiModelProperty(notes = "Id of the request")
    private Integer id;
    @ApiModelProperty(notes = "Loan amount")
    private Double loanAmount;
    @ApiModelProperty(notes = "Interest rate")
    private Integer interestRate;
    @ApiModelProperty(notes = "Number of payments")
    private Integer numOfPayments;
    @ApiModelProperty(notes = "Payment frequency")
    private Integer paymentFreq;
    @ApiModelProperty(notes = "Result")
    private ResultDto result;
}
