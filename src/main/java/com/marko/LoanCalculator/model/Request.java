package com.marko.LoanCalculator.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Request {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "loanamount", nullable = false)
    private Double loanamount;

    @Basic
    @Column(name = "interestrate", nullable = false)
    private Integer interestrate;

    @Basic
    @Column(name = "numofpayments", nullable = false)
    private Integer numofpayments;

    @Basic
    @Column(name = "paymentfreq", nullable = false)
    private Integer paymentfreq;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idresult", referencedColumnName = "id")
    private Result result;
}
