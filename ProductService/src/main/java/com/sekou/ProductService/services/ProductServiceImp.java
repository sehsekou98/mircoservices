package com.sekou.ProductService.services;

import com.sekou.ProductService.entity.Product;
import com.sekou.ProductService.exception.ProductServiceCustomException;
import com.sekou.ProductService.model.ProductRequest;
import com.sekou.ProductService.model.ProductResponse;
import com.sekou.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImp implements ProductService{



    @Autowired
    private  ProductRepository productRepository;

    // Adding products to chart
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);

        log.info("Product Created.");
        return product.getProductId();
    }

    // Get product by its ID
    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the product for productId: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product not found with given Id", "PRODUCT_NOT_FOUND"));

         ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);
        return productResponse;
    }
}
