package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    CategoryModel findBySubCategory(String subCategory);
}
