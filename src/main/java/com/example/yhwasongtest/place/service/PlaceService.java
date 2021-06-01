package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    private PlaceRepository placeRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, ReviewRepository reviewRepository) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    public PlaceModel putPlace(PlaceModel placeModel) throws Exception {
        PlaceModel existPlace = placeRepository.findByName(placeModel.getName());
        if (existPlace == null)
            return placeRepository.save(placeModel);
        else {
            existPlace.setArea(placeModel.getArea());
            existPlace.setName(placeModel.getName());
            existPlace.setNumber(placeModel.getNumber());
            existPlace.setUrl(placeModel.getUrl());
            placeRepository.save(existPlace);

            return existPlace;
        }
    }

    public void deletePlace(String name) {
        PlaceModel placeModel = placeRepository.findByName(name);
        placeRepository.delete(placeModel);
    }

    public ReviewModel putReview(ReviewModel review) throws Exception{
        if( review.getUserId() == 0 || review.getPlaceId() == 0 ) {
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
