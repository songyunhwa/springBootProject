package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1")
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping(value = "/place")
    public ResponseEntity getPlace(String name) {
        PlaceModel placeModel = placeService.getPlace(name);
        return new ResponseEntity<>(placeModel, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping(value = "/place")
    public ResponseEntity putPlace(HttpServletRequest request, @RequestBody PlaceModel model) {
        if (request.getSession().getAttribute("login") == null) {
            return new ResponseEntity("로그인이 안됬습니다.", HttpStatus.UNAUTHORIZED);
        }
        String result = "";
        PlaceModel placeModel = new PlaceModel();
        try {

            placeModel.setName(model.getName()); // 장소 이름
            placeModel.setArea(model.getArea()); // 지역

            placeModel = placeService.putPlace(placeModel);

            return new ResponseEntity<>(placeModel, HttpStatus.OK);

        } catch (Exception error) {
            System.err.println("putPlace Error ==> " + error);
            return new ResponseEntity<>(placeModel, HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/place/{name}")
    public ResponseEntity deletePlace(HttpServletRequest request,
                                      @RequestParam(name = "name",required = true) String name) {
        if (request.getSession().getAttribute("login") == null) {
            return new ResponseEntity("로그인이 안됬습니다.", HttpStatus.UNAUTHORIZED);
        }
        placeService.deletePlace(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping(value = "/place/review")
    public ResponseEntity putReview(HttpServletRequest request, ReviewModel review) {
        if (request.getSession().getAttribute("login") == null) {
            return new ResponseEntity("로그인이 안됬습니다.", HttpStatus.UNAUTHORIZED);
        }
        try {
            ReviewModel reviewModel = placeService.putReview(review);

            return new ResponseEntity<>(reviewModel, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("PlaceController.js : putReview exception cause :" , e.toString());
            return new ResponseEntity("리퀘스트 값이 없습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping(value = "/place/review")
    public ResponseEntity deleteReview(HttpServletRequest request,
                                       @RequestParam(name = "userName",required = true) String userName,
                                       @RequestParam(name = "placeName",required = true) String placeName
                                       ) {
        if (request.getSession().getAttribute("login") == null) {
            return new ResponseEntity("로그인이 안됬습니다.", HttpStatus.UNAUTHORIZED);
        }
        placeService.deleteReview(userName, placeName);
        return new ResponseEntity(HttpStatus.OK);
    }

}
