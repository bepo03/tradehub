package com.bepo.tradehub.product.repository;

import com.bepo.tradehub.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
