package com.bepo.tradehub.product.dto;

import com.bepo.tradehub.category.entity.Category;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "id", "name"
})
public class ProductCategoryResponse {

    private Long id;
    private String name;

    public static ProductCategoryResponse from(Category category) {
        return ProductCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
