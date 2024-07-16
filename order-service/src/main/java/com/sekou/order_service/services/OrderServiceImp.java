package com.sekou.order_service.services;


import com.sekou.order_service.entity.Order;
import com.sekou.order_service.external.client.PaymentService;
import com.sekou.order_service.external.client.ProductService;
import com.sekou.order_service.model.OrderRequest;
import com.sekou.order_service.repository.OrderRepository;
import com.sekou.order_service.request.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("Placing order Request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("Created")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

         order = orderRepository.save(order);

         log.info("Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the Order Status to Placed ");
            orderStatus = "Placed";
        } catch (Exception e) {
              log.info("Error occurred in payment. Changing order Status to Payment_Failure");
              orderStatus = "Payment_Failed";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

         log.info("Order placed successfully with order Id: {}", order);
        return order.getId();
    }
}
