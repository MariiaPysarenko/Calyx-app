package com.calyx.service.impl;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.mapper.ProductMapper;
import com.calyx.model.Product;
import com.calyx.repository.ProductRepository;
import com.calyx.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void validateProductInfo(ProductRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }

        if (request.caloriesPer100g() < 0) {
            throw new IllegalArgumentException("Calories must be >= 0");
        }

        if (request.proteinsPer100g() < 0) {
            throw new IllegalArgumentException("Proteins must be >= 0");
        }

        if (request.fatsPer100g() < 0) {
            throw new IllegalArgumentException("Fats must be >= 0");
        }

        if (request.carbsPer100g() < 0) {
            throw new IllegalArgumentException("Carbs must be >= 0");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }
    }

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        validateProductInfo(request);

        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        validateId(id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        validateId(id);
        validateProductInfo(request);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(request.name());
        existingProduct.setCategory(request.category() == null || request.category().isBlank()
                ? existingProduct.getCategory()
                : request.category().trim().toLowerCase());
        existingProduct.setCaloriesPer100g(request.caloriesPer100g());
        existingProduct.setProteinsPer100g(request.proteinsPer100g());
        existingProduct.setFatsPer100g(request.fatsPer100g());
        existingProduct.setCarbsPer100g(request.carbsPer100g());

        Product updatedProduct = productRepository.update(existingProduct);

        return ProductMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        validateId(id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(product.getId());
    }
}