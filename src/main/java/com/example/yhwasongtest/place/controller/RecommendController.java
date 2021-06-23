package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.WishedModel;
import com.example.yhwasongtest.place.service.RecommendService;
import org.json.JSONArray;
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
public class RecommendController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);

    private RecommendService recommendService;

    @Autowired
    public void RecommendController(RecommendService recommendService){
        this.recommendService = recommendService;
    }

    /**
     *
     * @param userName 추천자 이름
     * @param id       장소 아이디
     * @return
     */
    @PostMapping(value = "/recommend")
    public ResponseEntity putRecommend(@RequestParam(name = "userName",required = true) String userName,
                                    @RequestParam(name = "id",required = true) long id) {

        try {
            recommendService.putRecommend(userName, id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/wished")
    public ResponseEntity putWished(@RequestParam(name = "userName",required = true) String userName,
                                       @RequestParam(name = "id",required = true) long id) {

        try {
            recommendService.putWished(userName, id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/wished")
    public ResponseEntity getWished(@RequestParam(name = "userName",required = true) String userName) {

        try {
            JSONArray jsonArray = recommendService.getWished(userName);

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }
}
