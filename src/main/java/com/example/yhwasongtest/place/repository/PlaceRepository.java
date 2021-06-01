package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {
    PlaceModel findByName(String name);
}
