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
        "id", "title", "price", "status", "createdAt"
})
public class ProductListResponse {

    private Long id;
    private String title;
    private Long price;
    private String status;
    private LocalDateTime createdAt;

    public static ProductListResponse toResponseList(Product product) {
        return ProductListResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .status(product.getStatus().toString())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
