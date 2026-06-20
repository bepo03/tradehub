package com.bepo.tradehub.product.service;

import com.bepo.tradehub.category.entity.Category;
import com.bepo.tradehub.category.exception.CategoryNotFoundException;
import com.bepo.tradehub.category.repository.CategoryRepository;
import com.bepo.tradehub.product.dto.ProductCreateRequest;
import com.bepo.tradehub.product.dto.ProductListResponse;
import com.bepo.tradehub.product.dto.ProductResponse;
import com.bepo.tradehub.product.dto.ProductUpdateRequest;
import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.product.exception.ProductInUseException;
import com.bepo.tradehub.product.exception.ProductNotFoundException;
import com.bepo.tradehub.product.repository.ProductRepository;
import com.bepo.tradehub.reservation.repository.TradeReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TradeReservationRepository tradeReservationRepository;

    @Transactional
    public ProductResponse createProduct(
            ProductCreateRequest request
    ) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        Product product = Product.toEntity(request, category);

        Product saveProduct = productRepository.save(product);

        return ProductResponse.from(saveProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse updateProduct(
            ProductUpdateRequest request,
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        product.updateEntity(request, category);

        Product updateProduct = productRepository.save(product);

        return ProductResponse.from(updateProduct);
    }

    @Transactional
    public void deleteProduct(
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (tradeReservationRepository.existsByProductId(productId)) {
            throw new ProductInUseException(productId);
        }

        productRepository.delete(product);
    }
}
