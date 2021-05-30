package com.example.yhwasongtest.place;

import com.example.yhwasongtest.place.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByName(String name);
}
