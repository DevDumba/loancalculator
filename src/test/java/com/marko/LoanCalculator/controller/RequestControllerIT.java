package com.marko.LoanCalculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marko.LoanCalculator.BaseIntegrationTest;
import com.marko.LoanCalculator.controller.dto.RequestDto;
import com.marko.LoanCalculator.controller.dto.ResultDto;
import com.marko.LoanCalculator.controller.dto.ScheduleDto;
import com.marko.LoanCalculator.model.Request;
import com.marko.LoanCalculator.model.Result;
import com.marko.LoanCalculator.repository.RequestRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RequestControllerIT extends BaseIntegrationTest {

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void calculate() throws JsonProcessingException {
        Double loanAmount = 20000.0;
        Integer interestRate = 5;
        Integer numOfPayments = 10;
        Integer paymentFreq = 4;

        Optional<Request> existedRequest = requestRepository.findExisted(Double.valueOf(loanAmount), interestRate, numOfPayments, paymentFreq);

        Response response;
        if(existedRequest.isPresent()) {
             response = given()
                     .spec(super.specification)
                     .param("loanAmount", loanAmount)
                     .param("interestRate", interestRate)
                     .param("numOfPayments", numOfPayments)
                     .param("paymentFreq", paymentFreq)
                     .when()
                     .post("/request")
                     .then()
                     .statusCode(200)
                     .extract()
                     .response();
        } else {
            response = given()
                    .spec(super.specification)
                    .param("loanAmount", loanAmount)
                    .param("interestRate", interestRate)
                    .param("numOfPayments", numOfPayments)
                    .param("paymentFreq", paymentFreq)
                    .when()
                    .post("/request")
                    .then()
                    .statusCode(201)
                    .extract()
                    .response();
        }

        //check results from response
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestDto requestDto = objectMapper.readValue(response.getBody().asString(), RequestDto.class);

        ResultDto result = requestDto.getResult();
        assertEquals(21400.6, result.getTotalPayments());
        assertEquals(1400.6, result.getTotalInterest());

        //check first schedule
        ScheduleDto firstSchedule = result.getSchedule().get(0);
        assertEquals(2140.06, firstSchedule.getPaymentAmount());
        assertEquals(1890.06, firstSchedule.getPrincipalAmount());
        assertEquals(250.0, firstSchedule.getInterestAmount());
        assertEquals(18109.94, firstSchedule.getBalanceOwed());

        //check last schedule
        ScheduleDto lastSchedule = result.getSchedule().stream().max(Comparator.comparing(ScheduleDto::getId)).get();
        assertEquals(2140.06, lastSchedule.getPaymentAmount());
        assertEquals(2113.64, lastSchedule.getPrincipalAmount());
        assertEquals(26.42, lastSchedule.getInterestAmount());
        assertEquals(0.0, lastSchedule.getBalanceOwed());
    }
}
