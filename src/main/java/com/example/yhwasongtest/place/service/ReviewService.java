package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public void ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public ReviewModel putReview(ReviewModel review) throws Exception {
        if (review.getUserId() == 0 || review.getPlaceId() == 0) {
            throw new Exception("Request 값이 없습니다.");
        }
        ReviewModel reviewModel = reviewRepository.findByUserIdAndPlaceId(review.getUserId(), review.getPlaceId());
        if (reviewModel == null)
            return reviewRepository.save(review);
        else {
            reviewModel.setContents(review.getContents());
            reviewModel.setStar(review.getStar());
            return review;
        }
    }

    public void deleteReview(String userName, String placeName) {
        ReviewModel reviewModel = reviewRepository.findByUserNameAndPlaceName(userName, placeName);
        reviewRepository.delete(reviewModel);
    }
}
