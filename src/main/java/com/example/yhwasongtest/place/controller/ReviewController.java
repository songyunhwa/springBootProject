package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.place.service.ReviewService;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/v1")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final PictureRepository pictureRepository;
    private final PlaceService placeService;


    @Autowired
    public ReviewController(ReviewService reviewService, PictureRepository pictureRepository, PlaceService placeService) {
        this.reviewService = reviewService;
        this.pictureRepository = pictureRepository;
        this.placeService = placeService;
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

    @PostMapping(value = "/review/image")
    public ResponseEntity putFile(@RequestPart(value="image", required=true) MultipartFile file){
        try {
            PictureModel pictureModel = reviewService.saveFile(file);
            return new ResponseEntity(pictureModel, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/review/image")
    public ResponseEntity getFile(@RequestParam("id") long id) {
        try{

            PictureModel pictureModel = pictureRepository.findById(id);
            String filename = pictureModel.getFileName();
            String encodeFileName = URLEncoder.encode(filename, "UTF-8");
            Resource file = reviewService.loadFile(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodeFileName + "\"")
                    .body(file);

        }catch (Exception e){
            logger.info("PlaceController.js error =>" , e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



