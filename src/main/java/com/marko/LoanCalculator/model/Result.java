package com.marko.LoanCalculator.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Result {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "totalpayments", nullable = false)
    private Double totalpayments;

    @Basic
    @Column(name = "totalinterest", nullable = false)
    private Double totalinterest;

    @OneToOne(mappedBy = "result")
    private Request request;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<Schedule> schedule;
}
