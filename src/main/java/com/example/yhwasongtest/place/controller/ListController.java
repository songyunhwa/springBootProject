package com.example.yhwasongtest.place.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.dto.ListDto;
import com.example.yhwasongtest.place.service.ListService;
import com.example.yhwasongtest.user.model.UserModel;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ListController {

    private ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    /**
     * 내 맛집 리스트 가져오기
     */
    @GetMapping(value = "/myList")
    public ResponseEntity getMyList(HttpServletRequest request) {

        try {
            HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }

            JSONArray jsonArray = listService.getMyList(user.getId());

            return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 내 맛집 리스트 추가
     * @param listDto
     * @param request
     * @return
     */
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

    @DeleteMapping(value = "/myList")
    public ResponseEntity deleteMyList(@RequestBody ListDto listDto,
                                    HttpServletRequest request){
        try {
            /*HttpSession httpSession = request.getSession(false);
            UserModel user = (UserModel)httpSession.getAttribute("login");

            if(user==null) {
                throw new Exception(ErrorMessage.NOT_LOGIN_INVALID.getMessage());
            }*/
            long userId = 159;
            listService.deletetMyList(userId, listDto.getPlaceId());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
