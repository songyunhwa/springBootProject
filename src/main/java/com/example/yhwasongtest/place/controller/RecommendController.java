package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.service.RecommendService;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
     *  장소 추천하기
     * @param userName 추천자 이름
     * @param id       장소 아이디
     * @return
     */
    @PostMapping(value = "/recommend")
    public ResponseEntity putRecommend(@RequestParam(name = "userName",required = true) String userName,
                                       @RequestParam(name = "id",required = true) long id){

        try {
            String result = recommendService.putRecommend(userName, id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 장소 찜하기
     * @param userName
     * @param id
     * @return
     */
    @PostMapping(value = "/wished")
    public ResponseEntity putWished(@RequestParam(name = "userName",required = true) String userName,
                                    @RequestParam(name = "id",required = true) long id){
        try {
            String result = recommendService.putWished(userName, id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 장소 찜한 것 가져오기
     * @param userName
     * @return
     */
    @GetMapping(value = "/wished")
    public ResponseEntity getWished(@RequestParam(name = "userName",required = true) String userName) {

        try {
            JSONArray jsonArray = recommendService.getWished(userName);

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 추천 배너
     * @param userName
     * @return
     */
    @GetMapping(value = "/recommend")
    public ResponseEntity getRecommend(@RequestParam(name = "userName",required = true) String userName) {

        try {
            JSONArray jsonArray = recommendService.getRecommend(userName);

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
