package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.service.ReviewService;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/v1")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private ReviewService reviewService;

    @Autowired
    public void ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/review")
    public ResponseEntity getReview(@RequestParam("id") long id) {

        try {
            List<ReviewModel> reviewModels = reviewService.getReview(id);
            JSONArray jsonArray = CommonCode.reviewConvertToJSON(reviewModels);
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/review")
    public ResponseEntity putReview(@RequestBody ReviewModel review) {

        try {
            ReviewModel reviewModel = reviewService.putReview(review);
            return new ResponseEntity<>(reviewModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/review/{id}")
    public ResponseEntity modifyReview(@PathVariable Long id, @RequestBody ReviewModel review) {

        try {
            reviewService.modifyReview(review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/review/{id}")
    public ResponseEntity deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}



