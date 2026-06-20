package com.bepo.tradehub.product.dto;

import com.bepo.tradehub.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchCondition {

    private String keyword;
    private Long categoryId;
    private ProductStatus status;
}
