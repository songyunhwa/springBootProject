package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.RecommendService;
import com.example.yhwasongtest.user.model.UserModel;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1")
public class RecommendController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);

    private RecommendService recommendService;

    @Autowired
    public void RecommendController(RecommendService recommendService){
        this.recommendService = recommendService;
    }

    @ApiOperation(value="장소 추천하기")
    @ApiImplicitParam(name = "id",value ="장소 아이디" ,required = true , dataType="long", paramType="query")
    @PostMapping(value = "/recommend")
    public ResponseEntity putRecommend(@RequestParam(name = "id",required = true) long id,
                                       HttpServletRequest request){

        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel user = (UserModel)httpSession.getAttribute("login");
            String result = recommendService.putRecommend(user.getId(), id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="장소 찜하기")
    @ApiImplicitParam(name = "id",value ="장소 아이디" ,required = true , dataType="long", paramType="query")
    @PostMapping(value = "/wished")
    public ResponseEntity putWished(@RequestParam(name = "id",required = true) long id,
                                    HttpServletRequest request){
        try {
            HttpSession httpSession = request.getSession(false);
            if (httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel user = (UserModel)httpSession.getAttribute("login");
            String result = recommendService.putWished(user.getId(), id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value="장소 찜한 것 검색")
    @GetMapping(value = "/wished")
    public ResponseEntity getWished(HttpServletRequest request, Pageable pageable) {

        try {
            HttpSession httpSession = request.getSession(false);
            if(httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel       user = (UserModel)httpSession.getAttribute("login");
            JSONObject jsonObject = recommendService.getWished(user.getId(), pageable);


            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="장소 추천 리스트 검색")
    @GetMapping(value = "/recommend")
    public ResponseEntity getRecommend(HttpServletRequest request) {

        try {
            HttpSession httpSession = request.getSession(false);
            if(httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel user = (UserModel) httpSession.getAttribute("login");
            JSONArray jsonArray = recommendService.getRecommend(user.getId());

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

}
