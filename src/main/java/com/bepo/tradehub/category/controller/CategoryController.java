package com.bepo.tradehub.category.controller;

import com.bepo.tradehub.category.dto.CategoryCreateRequest;
import com.bepo.tradehub.category.dto.CategoryListResponse;
import com.bepo.tradehub.category.dto.CategoryResponse;
import com.bepo.tradehub.category.dto.CategoryUpdateRequest;
import com.bepo.tradehub.category.service.CategoryService;
import com.bepo.tradehub.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "카테고리 등록", description = "새 카테고리를 등록합니다.")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity
                .created(URI.create("/api/categories/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "카테고리 목록 조회", description = "전체 카테고리 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<CategoryListResponse>>> getCategories() {
        List<CategoryListResponse> responses = categoryService.getCategories();

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "카테고리 상세 조회", description = "카테고리 ID로 카테고리 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(
            @Parameter(description = "카테고리 ID", example = "1")
            @PathVariable Long categoryId
    ) {
        CategoryResponse response = categoryService.getCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정", description = "카테고리 ID로 카테고리 정보를 수정합니다.")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @RequestBody @Valid CategoryUpdateRequest request,
            @Parameter(description = "카테고리 ID", example = "1")
            @PathVariable Long categoryId
    ) {
        CategoryResponse response = categoryService.updateCategory(request, categoryId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제", description = "카테고리 ID로 카테고리를 삭제합니다.")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "카테고리 ID", example = "1")
            @PathVariable Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();
    }
}
