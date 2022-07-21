package com.marko.LoanCalculator.repository;

import com.marko.LoanCalculator.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query(value = "select r from Request r where r.loanamount = :loanAmount and r.interestrate = :interestRate and r.numofpayments = :numOfPayments and r.paymentfreq = :paymentFreq")
    Optional<Request> findExisted(@Param("loanAmount") Double loanAmount, @Param("interestRate") Integer interestRate,
                                  @Param("numOfPayments") Integer numOfPayments, @Param("paymentFreq") Integer paymentFreq);
}
