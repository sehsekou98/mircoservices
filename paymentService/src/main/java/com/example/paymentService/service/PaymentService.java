package com.example.paymentService.service;

import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(Long orderId);
}
