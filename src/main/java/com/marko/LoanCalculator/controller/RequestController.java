package com.marko.LoanCalculator.controller;

import com.marko.LoanCalculator.controller.dto.RequestDto;
import com.marko.LoanCalculator.controller.mapper.RequestMapper;
import com.marko.LoanCalculator.model.Request;
import com.marko.LoanCalculator.repository.RequestRepository;
import com.marko.LoanCalculator.service.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@CrossOrigin
@RestController
@Transactional
@RequestMapping("/api/request")
@Api(value="Request Management System")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RequestService requestService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    /**
     * {@code POST} : Calculate loan
     * @return the {@link org.springframework.http.ResponseEntity<com.marko.LoanCalculator.controller.dto.RequestDto>}
     */
    @ApiOperation(value = "calculate loan")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully calculated loan"),
            @ApiResponse(code = 200, message = "Get existed results")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<RequestDto> calculate(@RequestParam(value = "loanAmount") Double loanAmount, @RequestParam(value = "interestRate") Integer interestRate,
                                                @RequestParam(value = "numOfPayments") Integer numOfPayments, @RequestParam(value = "paymentFreq") Integer paymentFreq) throws URISyntaxException {
        LOGGER.debug("REST request to calculate loan with params -> loan amount : {}, interest rate : {}, number of payments : {}, payment frequency : {}",
                loanAmount, interestRate, numOfPayments, paymentFreq);
        Optional<Request> existedRequest = requestRepository.findExisted(loanAmount, interestRate, numOfPayments, paymentFreq);
        RequestDto requestDto;
        //if that request already exists, do not save, just return to response
        if(existedRequest.isPresent()) {
            requestDto = requestMapper.toDto(existedRequest.get());
            return ResponseEntity.ok().body(requestDto);
        } else {
            //call the service method to calculate and save request and results
            requestDto = requestService.calculate(loanAmount, interestRate, numOfPayments, paymentFreq);
            return ResponseEntity.created(new URI("/api/request")).body(requestDto);
        }
    }
}
