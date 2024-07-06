package com.sekou.ProductService.services;

import com.sekou.ProductService.model.ProductRequest;
import com.sekou.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, long quantity);
}
