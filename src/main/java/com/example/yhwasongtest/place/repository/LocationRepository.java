package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationModel, Long> {

}
