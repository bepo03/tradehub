package com.bepo.tradehub.product.controller;

import com.bepo.tradehub.global.common.ApiResponse;
import com.bepo.tradehub.product.dto.ProductCreateRequest;
import com.bepo.tradehub.product.dto.ProductListResponse;
import com.bepo.tradehub.product.dto.ProductResponse;
import com.bepo.tradehub.product.dto.ProductUpdateRequest;
import com.bepo.tradehub.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid ProductCreateRequest request
    ) {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity
                .created(URI.create("/api/products/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductListResponse>>> getProducts() {
        List<ProductListResponse> responses = productService.getProducts();

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.getProduct(productId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @RequestBody @Valid ProductUpdateRequest request,
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.updateProduct(request, productId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
