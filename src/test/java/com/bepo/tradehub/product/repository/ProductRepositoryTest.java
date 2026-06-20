package com.bepo.tradehub.product.repository;

import com.bepo.tradehub.category.entity.Category;
import com.bepo.tradehub.category.repository.CategoryRepository;
import com.bepo.tradehub.product.dto.ProductSearchCondition;
import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.product.entity.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllSearchesProductsByKeyword() {
        Category category = saveCategory("Digital");
        saveProduct(category, "Keyboard", ProductStatus.SELLING);
        saveProduct(category, "Mouse", ProductStatus.SELLING);

        ProductSearchCondition condition = ProductSearchCondition.builder()
                .keyword("key")
                .build();

        Page<Product> result = productRepository.findAll(
                ProductSpecification.byCondition(condition),
                PageRequest.of(0, 10)
        );

        assertThat(result.getContent())
                .extracting(Product::getTitle)
                .containsExactly("Keyboard");
    }

    @Test
    void findAllFiltersProductsByCategoryId() {
        Category digital = saveCategory("Digital");
        Category furniture = saveCategory("Furniture");
        saveProduct(digital, "Keyboard", ProductStatus.SELLING);
        saveProduct(furniture, "Desk", ProductStatus.SELLING);

        ProductSearchCondition condition = ProductSearchCondition.builder()
                .categoryId(digital.getId())
                .build();

        Page<Product> result = productRepository.findAll(
                ProductSpecification.byCondition(condition),
                PageRequest.of(0, 10)
        );

        assertThat(result.getContent())
                .extracting(Product::getTitle)
                .containsExactly("Keyboard");
    }

    @Test
    void findAllFiltersProductsByStatus() {
        Category category = saveCategory("Digital");
        saveProduct(category, "Keyboard", ProductStatus.SELLING);
        saveProduct(category, "Monitor", ProductStatus.RESERVED);

        ProductSearchCondition condition = ProductSearchCondition.builder()
                .status(ProductStatus.RESERVED)
                .build();

        Page<Product> result = productRepository.findAll(
                ProductSpecification.byCondition(condition),
                PageRequest.of(0, 10)
        );

        assertThat(result.getContent())
                .extracting(Product::getTitle)
                .containsExactly("Monitor");
    }

    @Test
    void findAllCombinesSearchConditions() {
        Category digital = saveCategory("Digital");
        Category furniture = saveCategory("Furniture");
        saveProduct(digital, "Keyboard", ProductStatus.SELLING);
        saveProduct(digital, "Keyboard Cover", ProductStatus.RESERVED);
        saveProduct(furniture, "Keyboard Desk", ProductStatus.SELLING);

        ProductSearchCondition condition = ProductSearchCondition.builder()
                .keyword("keyboard")
                .categoryId(digital.getId())
                .status(ProductStatus.SELLING)
                .build();

        Page<Product> result = productRepository.findAll(
                ProductSpecification.byCondition(condition),
                PageRequest.of(0, 10)
        );

        assertThat(result.getContent())
                .extracting(Product::getTitle)
                .containsExactly("Keyboard");
    }

    @Test
    void findAllAppliesPaging() {
        Category category = saveCategory("Digital");
        saveProduct(category, "Keyboard", ProductStatus.SELLING);
        saveProduct(category, "Mouse", ProductStatus.SELLING);
        saveProduct(category, "Monitor", ProductStatus.SELLING);

        ProductSearchCondition condition = ProductSearchCondition.builder().build();

        Page<Product> result = productRepository.findAll(
                ProductSpecification.byCondition(condition),
                PageRequest.of(0, 2)
        );

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    private Category saveCategory(String name) {
        return categoryRepository.save(Category.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    private Product saveProduct(Category category, String title, ProductStatus status) {
        return productRepository.save(Product.builder()
                .category(category)
                .title(title)
                .description(title + " description")
                .price(10000L)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }
}
