package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.dto.ListDto;
import com.example.yhwasongtest.place.service.ListService;
import com.example.yhwasongtest.user.model.UserModel;
import io.swagger.annotations.*;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/v1")
public class ListController {

    private ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @ApiOperation(value="맛집 리스트 검색")
    @GetMapping(value = "/myList")
    public ResponseEntity getMyList(HttpServletRequest request) {

        try {
            HttpSession httpSession = request.getSession(false);
            if(httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel user = (UserModel)httpSession.getAttribute("login");

            JSONArray jsonArray = listService.getMyList(user.getId());

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value="내 맛집 리스트 추가")
    @PostMapping(value = "/myList")
    public ResponseEntity putMyList(@RequestBody ListDto listDto,
                                    HttpServletRequest request){
        try {
            /*HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }*/
            long userId = 159;
            listService.putMyList(userId, listDto);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="내 맛집 리스트 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "placeId",value ="장소 아이디" ,required = true , dataType="long", paramType="query"),
    })
    @DeleteMapping(value = "/myList")
    public ResponseEntity deleteMyList(@RequestParam long placeId,
                                    HttpServletRequest request){
        try {
            HttpSession httpSession = request.getSession(false);
            if(httpSession == null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }
            UserModel user = (UserModel)httpSession.getAttribute("login");

            listService.deletetMyList(user.getId(), placeId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
