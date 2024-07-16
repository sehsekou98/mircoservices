package com.sekou.order_service.services;

import com.sekou.order_service.model.OrderRequest;
import com.sekou.order_service.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
