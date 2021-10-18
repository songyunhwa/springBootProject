package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.SearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiOperation(value="장소 검색", notes = "카테고리(subCategory) 리스트 검색으로 카테고리를 본 다음 하면 됩니다.")
    @ApiImplicitParam(name = "subCategory",value ="카테고리/이름" ,required = true , dataType="String", paramType="query")
    @GetMapping(value = "/dessert")
    public ResponseEntity getDessert(@RequestParam("subCategory") String subCategory) {

        try {
            List<PlaceModel> placeModels = searchService.getDessert(subCategory);
            JSONArray jsonArray = CommonCode.convertToJSON(placeModels);
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParam(name = "placeName",value ="장소 이름" ,required = true , dataType="String", paramType="query")
    @PostMapping(value = "/location")
    public ResponseEntity setLocation(@RequestParam("placeName") String placeName) {

        try {
             searchService.setLocation(placeName);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/city")
    public ResponseEntity getCities() {

        try {
            JSONObject result = searchService.getCities();

            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParam(name = "address",value ="시" ,required = true , dataType="String", paramType="query")
    @GetMapping(value = "/location")
    public ResponseEntity getCityPlace(@RequestParam("address") String address) {

        try {
            JSONArray result = searchService.getLocationPlaces(address);

            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
