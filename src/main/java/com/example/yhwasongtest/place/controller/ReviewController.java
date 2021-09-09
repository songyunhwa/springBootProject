package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.place.service.ReviewService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/v1")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final PictureRepository pictureRepository;
    private final PlaceService placeService;


    @Autowired
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, PictureRepository pictureRepository, PlaceService placeService) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.pictureRepository = pictureRepository;
        this.placeService = placeService;
    }

    @GetMapping(value = "/review")
    public ResponseEntity getReview(@RequestParam("id") long id) {

        try {
            List<ReviewModel> reviewModels = reviewService.getReview(id);
            JSONArray jsonArray = new JSONArray();
            if(reviewModels.size() > 0) {
                jsonArray = CommonCode.reviewConvertToJSON(reviewModels);
            }
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/review")
    public ResponseEntity putReview(@RequestBody ReviewModel review) {

        try {
            ReviewModel reviewModel = reviewService.putReview(review);
            return new ResponseEntity<>(reviewModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/review/{id}")
    public ResponseEntity modifyReview(@PathVariable Long id, @RequestBody ReviewModel review) {

        try {
            reviewService.modifyReview(review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/review/{id}")
    public ResponseEntity deleteReview(@PathVariable long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/review/image")
    public ResponseEntity putFile(@RequestPart(value = "image", required = true) List<MultipartFile> files) {
        try {
            if(files.isEmpty()){
                throw new Exception(ErrorMessage.PUT_FILE_INVALID.getMessage());
            }
            reviewService.saveFile(files);
            return new ResponseEntity(null, HttpStatus.OK);
        } catch (Exception e) {
            if(e.getMessage().equals(ErrorMessage.PUT_FILE_INVALID.getMessage())) {
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/review/image")
    public ResponseEntity removeFile(@RequestParam("id") long id) {
        try {

            reviewService.deleteFile(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



