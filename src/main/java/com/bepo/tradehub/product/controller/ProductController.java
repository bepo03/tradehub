package com.bepo.tradehub.product.controller;

import com.bepo.tradehub.product.dto.ProductCreateRequest;
import com.bepo.tradehub.product.dto.ProductListResponse;
import com.bepo.tradehub.product.dto.ProductResponse;
import com.bepo.tradehub.product.dto.ProductUpdateRequest;
import com.bepo.tradehub.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductListResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(
            @PathVariable Long productId
    ) {
        return productService.getProduct(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
            @RequestBody ProductUpdateRequest request,
            @PathVariable Long productId
    ) {
        return productService.updateProduct(request, productId);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);
    }
}
