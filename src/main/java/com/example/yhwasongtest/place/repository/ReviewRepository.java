package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<ReviewModel, Long> {
    List<ReviewModel> findByPlaceId(long placeId);
    ReviewModel findById(long id);
    ReviewModel findByUserIdAndPlaceId(long userId, long placeId);
}
