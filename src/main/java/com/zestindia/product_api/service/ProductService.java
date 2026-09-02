package com.zestindia.product_api.service;

import com.zestindia.product_api.dto.product.ProductRequest;
import com.zestindia.product_api.dto.product.ProductResponse;
import com.zestindia.product_api.entity.Product;
import com.zestindia.product_api.exception.ResourceNotFoundException;
import com.zestindia.product_api.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setCreatedBy("SYSTEM");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        return mapToResponse(product);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        product.setProductName(request.getProductName());
        product.setModifiedBy("SYSTEM");
        product.setModifiedOn(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getCreatedBy(),
                product.getCreatedOn(),
                product.getModifiedBy(),
                product.getModifiedOn()
        );
    }
}

