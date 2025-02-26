package com.example.paymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private long paymentId;
    private String Status;
    private  PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private long orderId;
}
