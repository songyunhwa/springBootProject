package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.DessertModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DessertRepository extends JpaRepository<DessertModel, Long> {
    List<DessertModel> findBySubCategory(String subCategory);
}
