package com.calyx.controller;

import com.calyx.dto.request.ProductRequest;
import com.calyx.dto.response.ProductResponse;
import com.calyx.service.ProductService;

import java.util.List;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public ProductResponse create(ProductRequest request) {
        return productService.addProduct(request);
    }

    public List<ProductResponse> getAll() {
        return productService.getAllProducts();
    }
}