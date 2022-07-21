package com.marko.LoanCalculator.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Schedule {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "number", nullable = false)
    private Integer number;

    @Basic
    @Column(name = "paymentamount", nullable = false)
    private Double paymentamount;

    @Basic
    @Column(name = "principalamount", nullable = false)
    private Double principalamount;

    @Basic
    @Column(name = "interestamount", nullable = false)
    private Double interestamount;

    @Basic
    @Column(name = "balanceowed", nullable = false)
    private Double balanceowed;

    @ManyToOne
    @JoinColumn(name = "idresult", referencedColumnName = "id")
    private Result result;
}
