package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.dto.PlaceDto;
import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.PlaceService;

import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ApiOperation(value="모든 장소 검색")
    @GetMapping(value = "/places")
    public ResponseEntity getPlaces(){

        try {
            List<PlaceModel> placeModels = placeService.getPlaces();

            JSONArray jsonArray = CommonCode.convertToJSON(placeModels);

            return  new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value="장소 검색")
    @ApiImplicitParam(name = "id",value ="장소 아이디" ,required = true , dataType="long", paramType="query")
    @GetMapping(value = "/place")
    public ResponseEntity getPlace(@RequestParam(name = "id") long id){
        try {
            PlaceModel placeModel = placeService.getPlace(id);
            List<PlaceModel> placeModels = new ArrayList<>();
            placeModels.add(placeModel);
            JSONArray jsonArray = CommonCode.convertToJSON(placeModels);

            return  new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value="장소 검색")
    @ApiImplicitParam(
            name = "msg"
            , value = "이름에 포함한 단어/채널"
            , required = true
            , dataType = "string"
            , paramType = "path"
            , defaultValue = "None")
    @GetMapping(value = "/place/{msg}")
    public ResponseEntity getPlaceByCategory(@PathVariable String msg){
        try {
            List<PlaceModel> placeModels = placeService.searchPlace(msg);
            JSONArray jsonArray = CommonCode.convertToJSON(placeModels);

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="장소 수정")
    @PostMapping(value = "/place")
    public ResponseEntity putPlace(@RequestBody PlaceDto placeDto) {

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession httpSession = request.getSession(false);
            if(httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            /*
            if(!user.getRole().equals("ROLE_ADMIN")){
                throw new Exception(ErrorMessage.PUT_PLACE_INVALID.getMessage());
            }*/

            // 지역에 대한 정보 저장
            PlaceModel placeModel = placeService.putPlace(placeDto);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) {
            System.err.println("putPlace Error ==> " + error);
            return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);

        }
    }
    @ApiOperation(value="장소 삭제")
    @ApiImplicitParam(
            name = "name"
            , value = "장소 이름"
            , required = true
            , dataType = "string"
            , paramType = "path"
            , defaultValue = "None")
    @DeleteMapping(value = "/place/{name}")
    public ResponseEntity deletePlace(@PathVariable(name = "name",required = true) String name) {

        placeService.deletePlace(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/classification")
    public void setClassification() {
        placeService.deletePlaceYoutube();
        placeService.deletePlaceContaingEng();
        placeService.getFoodCategory();
        placeService.getDessertCategory();
    }


    @ApiOperation(value="카테고리(subCategory) 리스트 검색")
    @GetMapping(value = "/category")
    public ResponseEntity getCategory(){
        try {
            List<DessertModel> dessertModels = placeService.getCategory();

            JSONArray jsonArray = CommonCode.categoryConvertToJSON(dessertModels);
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
