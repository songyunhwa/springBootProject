package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewService;

    @Autowired
    public void ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/review")
    public ResponseEntity putReview(@RequestBody ReviewModel review) {

        try {
            ReviewModel reviewModel = reviewService.putReview(review);

            return new ResponseEntity<>(reviewModel, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/review")
    public ResponseEntity deleteReview(
            @RequestParam(name = "userName",required = true) String userName,
            @RequestParam(name = "placeName",required = true) String placeName
    ) {
        reviewService.deleteReview(userName, placeName);
        return new ResponseEntity(HttpStatus.OK);
    }

}



