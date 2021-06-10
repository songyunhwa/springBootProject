package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.FoodModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository  extends JpaRepository<FoodModel, Long> {
    FoodModel findByIncludedContaining(String included);
}
