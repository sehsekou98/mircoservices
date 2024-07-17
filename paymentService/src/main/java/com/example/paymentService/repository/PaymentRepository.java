package com.example.paymentService.repository;

import com.example.paymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<TransactionDetails, Long> {

    TransactionDetails findByOrderId(Long orderId);
}
