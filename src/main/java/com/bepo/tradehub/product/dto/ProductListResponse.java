package com.bepo.tradehub.product.dto;

import com.bepo.tradehub.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id", "title", "price", "status", "category", "createdAt"
})
public class ProductListResponse {

    private Long id;
    private String title;
    private Long price;
    private String status;
    private ProductCategoryResponse category;
    private LocalDateTime createdAt;

    public static ProductListResponse from(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .status(product.getStatus().toString())
                .category(ProductCategoryResponse.from(product.getCategory()))
                .createdAt(product.getCreatedAt())
                .build();
    }
}
