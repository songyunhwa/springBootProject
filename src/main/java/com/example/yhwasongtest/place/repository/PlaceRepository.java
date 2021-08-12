package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.FoodModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {
    PlaceModel findById(long id);
    PlaceModel findByName(String name);

    List<PlaceModel> findBySubCategory(String subCategory);

    List<PlaceModel> findBySubCategoryOrderByViewDesc(String subCategory);

    List<PlaceModel> findByNameContaining(String name);

    // 기본 목록
    @Query(value = "SELECT * FROM test.place p WHERE p.sub_category != '디저트' And p.view >= 1 Order By p.view Desc;", nativeQuery = true)
    List<PlaceModel> findByViewAndSubCategory();

    // 영어로 된 place 는 지운다.
    @Query(value = "SELECT * FROM test.place p WHERE p.name REGEXP '^[A-Za-z]';", nativeQuery = true)
    List<PlaceModel> findByNameContaingEng();

    // 검색 기능
    @Query(value = "SELECT * FROM test.place p " +
            "           WHERE p.name like concat('%', :msg, '%'); " , nativeQuery = true)
    List<PlaceModel> findByNameContaing(@Param("msg") String msg);


    @Query(value = "SELECT p.* FROM test.place p " +
            "       JOIN (" +
            "           SELECT *\n" +
            "           FROM test.youtube y\n" +
            "           WHERE y.channel_title like %:msg% \n" +
            "           )as y " +
            "       ON y.place_id = p.id;", nativeQuery = true)
    List<PlaceModel> findByChannelTitle(@Param("msg") String msg);

    // 추천 목록
    @Query(value = "SELECT p.* , (p.view + p.recommend) as sum FROM test.place p WHERE p.sub_category = :category order by sum desc;", nativeQuery = true)
    List<PlaceModel> findBySubCategoryOrderByRecommendDecsViewDesc(@Param("category") String category);


}
