package com.marko.LoanCalculator;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseIntegrationTest {

    protected RequestSpecification specification;

    @BeforeEach
    public void setUp() {
        specification =
                new RequestSpecBuilder()
                        .setBaseUri("http://localhost:8080")
                        .setBasePath("/api")
                        .setPort(8080)
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .build();
    }
}
