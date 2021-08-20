package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.dto.PlaceDto;
import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.PlaceService;

import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/v1")
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    private final PlaceService placeService;
    private final YoutubeService youtubeService;

    @Autowired
    public PlaceController(PlaceService placeService, YoutubeService youtubeService) {
        this.placeService = placeService;
        this.youtubeService = youtubeService;
    }

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

    @PostMapping(value = "/place")
    public ResponseEntity putPlace(@RequestBody PlaceDto placeDto) {

        try {
            // 지역에 대한 정보 저장
            PlaceModel placeModel = placeService.putPlace(placeDto);

            return new ResponseEntity<>(placeModel, HttpStatus.OK);
        } catch (Exception error) {
            System.err.println("putPlace Error ==> " + error);
            return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping(value = "/place/{name}")
    public ResponseEntity deletePlace(@PathVariable(name = "name",required = true) String name) {

        placeService.deletePlace(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/location")
    public ResponseEntity getLocation(){
        String result = placeService.getLocation();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public void addView(@CookieValue(name = "login", defaultValue = "null") String cookie,
                             @RequestParam(name = "id") long id,
                             @RequestParam(name = "placeName") String placeName,
                             HttpServletResponse response){
        // 쿠키에 아이디가 없다면 추가해주기.
        if(!cookie.contains("loginCookie" + placeName)){
            cookie += id + "/";
            placeService.addView(placeName);
        }
        response.addCookie(new Cookie("loginCookie" +placeName, cookie));

    }

    @GetMapping(value = "/classification")
    public void setClassification() {
        placeService.deletePlaceYoutube();
        placeService.deletePlaceContaingEng();
        placeService.getFoodCategory();
        placeService.getDessertCategory();
    }

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
