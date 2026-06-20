package com.bepo.tradehub.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리명은 필수입니다.")
    @Size(max = 50, message = "카테고리명은 50자 이하로 입력해주세요.")
    private String name;
}
