package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationModel, Long> {
    List<LocationModel> findByPlaceId(Long placeId);
    LocationModel findByPlaceIdAndAddress(Long placeId, String address);
    List<LocationModel> findByAddressContains(String address);

}
