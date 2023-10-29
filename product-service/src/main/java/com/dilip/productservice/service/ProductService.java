package com.dilip.productservice.service;

import com.dilip.productservice.dto.ProductRequest;
import com.dilip.productservice.dto.ProductResponse;
import com.dilip.productservice.model.Product;
import com.dilip.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
/****
 * Not needed as it will be added by RequiredArgsConstructor by Lombok at runtime.

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
 }*/



    public void createProduct (ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved successfully",product.getId());

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}


