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
        "id", "title", "description", "price", "status", "createdAt", "updatedAt"
})
public class ProductResponse {

    private Long id;
    private String title;
    private String description;
    private Long price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus().toString())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
