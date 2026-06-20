package com.bepo.tradehub.category.dto;

import com.bepo.tradehub.category.entity.Category;
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
        "id", "name", "createdAt"
})
public class CategoryListResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static CategoryListResponse from(Category category) {
        return CategoryListResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
