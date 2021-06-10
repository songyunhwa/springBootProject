package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.place.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class RecommendController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);

    private RecommendService recommendService;

    @Autowired
    public void RecommendController(RecommendService recommendService){
        this.recommendService = recommendService;
    }

    @PostMapping(value = "/recommend")
    public ResponseEntity putRecommend(@RequestParam(name = "userName",required = true) String userName,
                                    @RequestParam(name = "placeName",required = true) String placeName) {

        try {
            recommendService.putRecommend(userName, placeName);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }
}
