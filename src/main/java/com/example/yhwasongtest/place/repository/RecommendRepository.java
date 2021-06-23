package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.RecommendModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendRepository  extends JpaRepository<RecommendModel, Long> {
    RecommendModel findByPlaceId(long id);

    @Query(value = "SELECT * FROM recommend AS r WHERE :user MEMBER OF r.userList" ,nativeQuery = true)
    List<RecommendModel> findByUser(@Param("user") String user);
}
