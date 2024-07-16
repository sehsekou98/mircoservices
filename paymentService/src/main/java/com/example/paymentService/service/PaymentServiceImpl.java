package com.example.paymentService.service;


import com.example.paymentService.entity.TransactionDetails;
import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{


    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}",  paymentRequest);

        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("Successful")
                .orderId(paymentRequest.getOrderId())
                .referenceCode(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        paymentRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails);

        return transactionDetails.getId();
    }
}
