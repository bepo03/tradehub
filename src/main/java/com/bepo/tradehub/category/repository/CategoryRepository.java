package com.bepo.tradehub.category.repository;

import com.bepo.tradehub.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
