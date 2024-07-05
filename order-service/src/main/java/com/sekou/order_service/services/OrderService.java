package com.sekou.order_service.services;

import com.sekou.order_service.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
