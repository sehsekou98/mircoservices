package com.example.paymentService.controller;


import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return  new ResponseEntity<>(
                paymentService.doPayment(paymentRequest),
                HttpStatus.OK);

    }
}
