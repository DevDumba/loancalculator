package com.marko.LoanCalculator.model;

public enum PaymentFrequency {
    MONTHLY(12),
    QUARTERLY(4),
    SEMI_ANNUAL(2),
    ANNUAL(1);

    public final Integer id;
    PaymentFrequency(int id) {
        this.id = id;
    }
}
