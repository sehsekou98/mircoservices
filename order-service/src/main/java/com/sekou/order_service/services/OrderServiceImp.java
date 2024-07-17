package com.sekou.order_service.services;


import com.sekou.order_service.entity.Order;
import com.sekou.order_service.exception.CustomException;
import com.sekou.order_service.external.client.PaymentService;
import com.sekou.order_service.external.client.ProductService;
import com.sekou.order_service.model.OrderRequest;
import com.sekou.order_service.model.OrderResponse;
import com.sekou.order_service.repository.OrderRepository;
import com.sekou.order_service.request.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    @Autowired
    private RestTemplate restTemplate;

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



    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get Order details for order Id : {}", orderId);


        Order order
                = orderRepository.findById(orderId)
                .orElseThrow( () -> new CustomException("Order not found for the order Id:" + orderId,
                        "NOT_FOUND",
                        404));

        log.info("Invoking Product Service to fetch the product for id:{}", order.getProductId());
        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class
        );

        assert productResponse != null;
        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .quantity(productResponse.getQuantity())
                .price(productResponse.getPrice())
                .build();


OrderResponse orderResponse =
   OrderResponse.builder()
           .orderId(order.getId())
           .orderStatus(order.getOrderStatus())
           .amount(order.getAmount())
           .orderDate(order.getOrderDate())
           .productDetails(productDetails)
           .build();
        return orderResponse;
    }
}
