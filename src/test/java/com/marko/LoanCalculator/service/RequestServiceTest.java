package com.marko.LoanCalculator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {

    @Test
    void calculateIRPM() {
        RequestService requestService = new RequestService();
        Double irpm = requestService.calculateIRPM(5,4);
        assertEquals(0.0125, irpm);
    }
}