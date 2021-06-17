package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.FoodModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {
    PlaceModel findByName(String name);
    List<PlaceModel> findBySubCategory(String subCategory);
    List<PlaceModel> findBySubCategoryOrderByViewDesc(String subCategory);
    List<PlaceModel> findByArea(String area);
    List<PlaceModel> findByNameContaining(String name);

    @Query(value = "SELECT * FROM ( SELECT * FROM test.place p WHERE p.sub_category != 'etc' ORDER By p.view Desc ) AS places GROUP By places.view, places.sub_category" ,nativeQuery = true)
    List<PlaceModel> findByViewAndSubCategory();

    @Query(value = "SELECT * FROM test.place p WHERE p.sub_category != 'etc' Order By p.view Desc" ,nativeQuery = true)
    List<PlaceModel> findByExceptEtcSubCategory();


}
