package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, PlaceRepository placeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    public List<ReviewModel> getReview(long id){
        return reviewRepository.findByPlaceId(id);
    }

    public ReviewModel putReview(ReviewModel review) throws Exception {

        UserModel userModel = userRepository.findByUsername(review.getUserName());
        PlaceModel placeModel = placeRepository.findById(review.getPlaceId());

        if (userModel == null || placeModel == null) {
            throw new Exception("Request 값이 없습니다.");
        }

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setUserId(review.getUserId());
        reviewModel.setUserName(review.getUserName());
        reviewModel.setPlaceId(review.getPlaceId());
        reviewModel.setPlaceName(review.getPlaceName());
        reviewModel.setContents(review.getContents());
        reviewModel.setPrevId(review.getPrevId());
        reviewModel.setStar(0);


        return reviewRepository.save(reviewModel);
    }

    public void modifyReview(ReviewModel review){
        Optional<ReviewModel> reviewModelOptional = reviewRepository.findById(review.getId());
        ReviewModel reviewModel = reviewModelOptional.get();
        reviewModel.setContents(review.getContents());
        reviewModel.setStar(review.getStar());
        reviewRepository.save(reviewModel);
    }

    public void deleteReview(long id) {
        Optional<ReviewModel> reviewModel = reviewRepository.findById(id);
        reviewRepository.delete(reviewModel.get());
    }
}
