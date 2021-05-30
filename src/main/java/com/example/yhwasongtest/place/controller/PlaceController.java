package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.model.Place;
import com.example.yhwasongtest.place.service.PlaceService;
import com.mysql.cj.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@RestController
@RequestMapping(value = "/api/v1")
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    private final PlaceService placeService;
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PutMapping(value = "/place")
    public ResponseEntity putPlace(HttpServletRequest request, Place model) {
        if (request.getSession().getAttribute("login") == null) {
            return new ResponseEntity("로그인이 안됬습니다.", HttpStatus.BAD_REQUEST);
        }
        String result = "";
        Place place = new Place();
        try {

            place.setName(model.getName()); // 장소 이름
            place.setArea(model.getArea()); // 지역

            place = placeService.putPlace(place);

        } catch (Exception error) {
            System.err.println("putPlace Error ==> " + error);

        }

        return new ResponseEntity<Place>(place, HttpStatus.OK);

    }

}
