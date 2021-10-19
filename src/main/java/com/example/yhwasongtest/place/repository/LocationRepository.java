package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationModel, Long> {
    List<LocationModel> findByPlaceId(Long placeId);
    LocationModel findByPlaceIdAndAddress(Long placeId, String address);

    @Query(value = "SELECT * FROM test.location WHERE address like concat('%', :address, '%');", nativeQuery = true)
    List<LocationModel> findByAddressContains(@Param("address") String address);

}
