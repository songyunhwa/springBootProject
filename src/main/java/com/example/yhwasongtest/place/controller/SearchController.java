package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.SearchService;
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

    @GetMapping(value = "/dessert/list")
    public ResponseEntity getDessert() {

        try {
            List<DessertModel> dessertModels = searchService.getDessert();
            JSONArray jsonArray = new JSONArray();
            for (DessertModel dessertModel : dessertModels) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dessertModel.getId());
                jsonObject.put("included", dessertModel.getIncluded());
                jsonObject.put("subCategory", dessertModel.getSubCategory());
                jsonArray.add(jsonObject);
            }
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/dessert")
    public ResponseEntity selectDessert(@RequestParam("subCategory") String subCategory) {

        try {
            List<PlaceModel> placeModels = searchService.selectDessert(subCategory);
            JSONArray jsonArray = CommonCode.convertToJSON(placeModels);
            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
