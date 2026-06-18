package com.bepo.tradehub.product.service;

import com.bepo.tradehub.product.dto.ProductCreateRequest;
import com.bepo.tradehub.product.dto.ProductListResponse;
import com.bepo.tradehub.product.dto.ProductResponse;
import com.bepo.tradehub.product.dto.ProductUpdateRequest;
import com.bepo.tradehub.product.entity.Product;
import com.bepo.tradehub.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(
            ProductCreateRequest request
    ) {
        Product product = Product.toEntity(request);

        Product saveProduct = productRepository.save(product);

        return ProductResponse.toResponse(saveProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductListResponse::toResponseList)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        return ProductResponse.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(
            ProductUpdateRequest request,
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        product.updateEntity(request);

        Product updateProduct = productRepository.save(product);

        return ProductResponse.toResponse(updateProduct);
    }

    @Transactional
    public void deleteProduct(
            Long productId
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

        productRepository.delete(product);
    }
}
