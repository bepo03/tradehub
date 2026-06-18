package com.bepo.tradehub.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {

    private String title;
    private String description;
    private Long price;
}
