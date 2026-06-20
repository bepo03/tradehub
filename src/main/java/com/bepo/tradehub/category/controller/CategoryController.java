package com.bepo.tradehub.category.controller;

import com.bepo.tradehub.category.dto.CategoryCreateRequest;
import com.bepo.tradehub.category.dto.CategoryListResponse;
import com.bepo.tradehub.category.dto.CategoryResponse;
import com.bepo.tradehub.category.dto.CategoryUpdateRequest;
import com.bepo.tradehub.category.service.CategoryService;
import com.bepo.tradehub.global.common.ApiResponse;
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
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity
                .created(URI.create("/api/categories/" + response.getId()))
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryListResponse>>> getCategories() {
        List<CategoryListResponse> responses = categoryService.getCategories();

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(
            @PathVariable Long categoryId
    ) {
        CategoryResponse response = categoryService.getCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @RequestBody @Valid CategoryUpdateRequest request,
            @PathVariable Long categoryId
    ) {
        CategoryResponse response = categoryService.updateCategory(request, categoryId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();
    }
}
