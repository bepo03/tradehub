package com.bepo.tradehub.product.controller;

import com.bepo.tradehub.global.common.ApiResponse;
import com.bepo.tradehub.global.common.PageResponse;
import com.bepo.tradehub.product.dto.ProductCreateRequest;
import com.bepo.tradehub.product.dto.ProductListResponse;
import com.bepo.tradehub.product.dto.ProductResponse;
import com.bepo.tradehub.product.dto.ProductSearchCondition;
import com.bepo.tradehub.product.dto.ProductUpdateRequest;
import com.bepo.tradehub.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 등록", description = "새 상품을 등록합니다.")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid ProductCreateRequest request
    ) {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity
                .created(URI.create("/api/products/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 검색, 필터링, 페이징 조건으로 조회합니다.")
    public ResponseEntity<ApiResponse<PageResponse<ProductListResponse>>> getProducts(
            @ParameterObject @ModelAttribute ProductSearchCondition condition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            @ParameterObject Pageable pageable
    ) {
        PageResponse<ProductListResponse> responses = productService.getProducts(condition, pageable);

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 ID로 상품 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.getProduct(productId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품 ID로 상품 정보를 수정합니다.")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @RequestBody @Valid ProductUpdateRequest request,
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.updateProduct(request, productId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품 ID로 상품을 삭제합니다.")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
