package com.bepo.tradehub.product.repository;

import com.bepo.tradehub.product.dto.ProductSearchCondition;
import com.bepo.tradehub.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> byCondition(ProductSearchCondition condition) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (condition.getKeyword() != null && !condition.getKeyword().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        "%" + condition.getKeyword().toLowerCase() + "%"
                ));
            }

            if (condition.getCategoryId() != null) {
                predicates.add(cb.equal(
                        root.get("category").get("id"),
                        condition.getCategoryId()
                ));
            }

            if (condition.getStatus() != null) {
                predicates.add(cb.equal(
                        root.get("status"),
                        condition.getStatus()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
