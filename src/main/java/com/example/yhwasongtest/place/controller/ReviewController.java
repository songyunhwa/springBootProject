package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.dto.ReviewDto;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.place.service.ReviewService;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.nimbusds.jose.util.IOUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.client.methods.HttpHead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final PictureRepository pictureRepository;

    @Autowired
    public ReviewController(ReviewService reviewService,PictureRepository pictureRepository ) {
        this.reviewService = reviewService;
        this.pictureRepository = pictureRepository;
    }

    @ApiOperation(value="장소 리뷰 검색")
    @ApiImplicitParam(name = "id",value ="장소 아이디" ,required = true , dataType="long", paramType="query")
    @GetMapping(value = "/review")
    public ResponseEntity getReview(@RequestParam("id") long id) {

        try {
            List<ReviewModel> reviewModels = reviewService.getReview(id);
            JSONArray jsonArray = new JSONArray();
            if (reviewModels.size() > 0) {
                for (ReviewModel reviewModel : reviewModels) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", reviewModel.getId());
                    jsonObject.put("userName", reviewModel.getUserName());
                    jsonObject.put("userId", reviewModel.getUserId());
                    jsonObject.put("contents", reviewModel.getContents());
                    jsonObject.put("star", reviewModel.getStar());
                    jsonObject.put("prevId", reviewModel.getPrevId());

                    PictureModel pictureModel = pictureRepository.findByReviewId(reviewModel.getId());
                    if (pictureModel != null) {
                        jsonObject.put("fileId", pictureModel.getId());
                        jsonObject.put("fileName", pictureModel.getFileName());
                    }
                    jsonArray.add(jsonObject);
                }
            }
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/review")
    public ResponseEntity putReview(@RequestBody ReviewDto review) {

        try {
            ReviewModel reviewModel = reviewService.putReview(review);
            return new ResponseEntity<>(reviewModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "리뷰 수정")
    @ApiImplicitParam(
            name = "id"
            , value = "리뷰 아이디"
            , required = true
            , dataType = "long"
            , paramType = "path"
            , defaultValue = "None")
    @PostMapping(value = "/review/{id}")
    public ResponseEntity modifyReview(@PathVariable Long id, @RequestBody ReviewDto review) {

        try {
            reviewService.modifyReview(review);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="리뷰 삭제")
    @ApiImplicitParam(
            name = "id"
            , value = "리뷰 아이디"
            , required = true
            , dataType = "long"
            , paramType = "path"
            , defaultValue = "None")
    @DeleteMapping(value = "/review/{id}")
    public ResponseEntity deleteReview(@PathVariable long id) {
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="파일 업로드" , produces = "multipart/form-data")
    @PostMapping(value = "/review/image")
    public ResponseEntity putFile(@RequestPart(value = "image", required = true) MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new Exception(ErrorMessage.PUT_FILE_INVALID.getMessage());
            }
            PictureModel reviewModel = reviewService.saveFile(file);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileId", reviewModel.getId());
            jsonObject.put("fileName", reviewModel.getFileName());
            return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ErrorMessage.PUT_FILE_INVALID.getMessage())) {
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="파일 삭제")
    @ApiImplicitParam(name = "id",value ="파일 아이디" ,required = true , dataType="long", paramType="query")
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



