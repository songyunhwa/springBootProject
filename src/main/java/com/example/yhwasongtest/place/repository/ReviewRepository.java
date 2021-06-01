package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<ReviewModel, Long> {
    ReviewModel findByUserIdAndPlaceId(long userId, long placeId);
    ReviewModel findByUserNameAndPlaceName(String userName, String placeName);
}
