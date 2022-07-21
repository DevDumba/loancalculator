package com.marko.LoanCalculator.service;

import com.marko.LoanCalculator.controller.dto.RequestDto;
import com.marko.LoanCalculator.controller.mapper.RequestMapper;
import com.marko.LoanCalculator.model.Request;
import com.marko.LoanCalculator.model.Result;
import com.marko.LoanCalculator.model.Schedule;
import com.marko.LoanCalculator.repository.RequestRepository;
import com.marko.LoanCalculator.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private ResultRepository resultRepository;

    public RequestDto calculate(Double loanAmount, Integer interestRate, Integer numOfPayments, Integer paymentFreq) {
        Request request = new Request();
        request.setLoanamount(loanAmount);
        request.setInterestrate(interestRate);
        request.setNumofpayments(numOfPayments);
        request.setPaymentfreq(paymentFreq);

        Result result = new Result();
        Double interestRatePerMonth;
        Double paymentAmount;

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        //calculate interest rate per month
        interestRatePerMonth = calculateIRPM(interestRate, paymentFreq);
        //calculate payment amount
        paymentAmount = calculatePaymentAmount(loanAmount, interestRatePerMonth, numOfPayments, df);
        //calculate and set total payments to result
        result.setTotalpayments(Double.valueOf(df.format(paymentAmount*numOfPayments)));
        //calculate and set total interest to result
        result.setTotalinterest(Double.valueOf(df.format(result.getTotalpayments()-loanAmount)));

        //calculate schedule
        List<Schedule> scheduleList = new ArrayList<>();
        int index = 0;
        for(int i = 1; i<=numOfPayments;i++) {
            Schedule schedule = new Schedule();
            if(scheduleList.isEmpty()){
                schedule.setNumber(i);
                schedule.setPaymentamount(paymentAmount);
                schedule.setInterestamount(Double.valueOf(df.format(loanAmount*interestRatePerMonth)));
                schedule.setPrincipalamount(Double.valueOf(df.format(paymentAmount-schedule.getInterestamount())));
                schedule.setBalanceowed(Double.valueOf(df.format(loanAmount-schedule.getPrincipalamount())));
            } else {
                Schedule lastSchedule = scheduleList.get(index);
                schedule.setNumber(i);
                if(i!=numOfPayments) {
                    schedule.setPaymentamount(paymentAmount);
                    schedule.setInterestamount(Double.valueOf(df.format(lastSchedule.getBalanceowed() * interestRatePerMonth)));
                    schedule.setPrincipalamount(Double.valueOf(df.format(paymentAmount - schedule.getInterestamount())));
                    schedule.setBalanceowed(Double.valueOf(df.format(lastSchedule.getBalanceowed() - schedule.getPrincipalamount())));
                //if it is last payment than settle all debts
                } else {
                    schedule.setPrincipalamount(lastSchedule.getBalanceowed());
                    schedule.setInterestamount(Double.valueOf(df.format(lastSchedule.getBalanceowed() * interestRatePerMonth)));
                    schedule.setPaymentamount(Double.valueOf(df.format(schedule.getPrincipalamount()+schedule.getInterestamount())));
                    schedule.setBalanceowed(0.00);
                }
                index++;
            }
            schedule.setResult(result);
            scheduleList.add(schedule);
        }
        //set schedule to result
        result.setSchedule(scheduleList);
        //set result to request and save request
        request.setResult(result);
        requestRepository.saveAndFlush(request);
        return requestMapper.toDto(request);
    }

    private Double calculatePaymentAmount(Double loanAmount, Double interestRatePerMonth, Integer numOfPayments, DecimalFormat df) {
        return Double.valueOf(df.format((loanAmount*interestRatePerMonth)*Math.pow(1+interestRatePerMonth, numOfPayments) / (Math.pow(1+interestRatePerMonth, numOfPayments) - 1)));
    }

    public Double calculateIRPM(Integer interestRate, Integer paymentFreq) {
        return Double.valueOf(interestRate) / 100 / Double.valueOf(paymentFreq);
    }
}
