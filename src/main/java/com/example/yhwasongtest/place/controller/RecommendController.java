package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.service.RecommendService;
import com.example.yhwasongtest.user.model.UserModel;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//@CrossOrigin(origins = "http://localhost:8080")
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
     * @param id       장소 아이디
     * @return
     */
    @PostMapping(value = "/recommend")
    public ResponseEntity putRecommend(@RequestParam(name = "id",required = true) long id,
                                       HttpServletRequest request){

        try {
            HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }

            String result = recommendService.putRecommend(user.getId(), id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 장소 찜하기
     * @param id    장소 아이디
     * @return
     */
    @PostMapping(value = "/wished")
    public ResponseEntity putWished(@RequestParam(name = "id",required = true) long id,
                                    HttpServletRequest request){
        try {
            HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }

            String result = recommendService.putWished(user.getId(), id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 장소 찜한 것 가져오기
     * @return
     */
    @GetMapping(value = "/wished")
    public ResponseEntity getWished(HttpServletRequest request) {

        try {
            HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }

            JSONArray jsonArray = recommendService.getWished(user.getId());

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 추천 배너
     * @return
     */
    @GetMapping(value = "/recommend")
    public ResponseEntity getRecommend(HttpServletRequest request) {

        try {
            HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }

            JSONArray jsonArray = recommendService.getRecommend(user.getId());

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

}
