package com.bepo.tradehub.category.service;

import com.bepo.tradehub.category.dto.CategoryCreateRequest;
import com.bepo.tradehub.category.dto.CategoryListResponse;
import com.bepo.tradehub.category.dto.CategoryResponse;
import com.bepo.tradehub.category.dto.CategoryUpdateRequest;
import com.bepo.tradehub.category.entity.Category;
import com.bepo.tradehub.category.exception.CategoryInUseException;
import com.bepo.tradehub.category.exception.CategoryNotFoundException;
import com.bepo.tradehub.category.repository.CategoryRepository;
import com.bepo.tradehub.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CategoryResponse createCategory(
            CategoryCreateRequest request
    ) {
        Category category = Category.toEntity(request);

        Category saveCategory = categoryRepository.save(category);

        return CategoryResponse.from(saveCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryListResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(
            Long categoryId
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return CategoryResponse.from(category);
    }

    @Transactional
    public CategoryResponse updateCategory(
            CategoryUpdateRequest request,
            Long categoryId
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.updateEntity(request);

        Category updateCategory = categoryRepository.save(category);

        return CategoryResponse.from(updateCategory);
    }

    @Transactional
    public void deleteCategory(
            Long categoryId
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        if (productRepository.existsByCategoryId(categoryId)) {
            throw new CategoryInUseException(categoryId);
        }

        categoryRepository.delete(category);
    }
}
