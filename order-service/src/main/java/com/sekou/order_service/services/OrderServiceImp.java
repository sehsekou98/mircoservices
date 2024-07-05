package com.sekou.order_service.services;


import com.sekou.order_service.entity.Order;
import com.sekou.order_service.model.OrderRequest;
import com.sekou.order_service.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("Placing order Request: {}", orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("Created")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

         order = orderRepository.save(order);

         log.info("Order placed successfully with order Id: {}", order);
        return order.getId();
    }
}
