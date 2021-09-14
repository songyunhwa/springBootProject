package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.dto.ListDto;
import com.example.yhwasongtest.place.model.MyListModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.MyListRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    private MyListRepository myListRepository;
    private ReviewRepository reviewRepository;
    private PlaceRepository placeRepository;

    @Autowired
    public ListService(MyListRepository myListRepository, ReviewRepository reviewRepository, PlaceRepository placeRepository) {
        this.myListRepository = myListRepository;
        this.reviewRepository = reviewRepository;
        this.placeRepository = placeRepository;
    }

    public JSONArray getMyList(long userId) {
        List<MyListModel> myListModel = myListRepository.findByUserId(userId);
        JSONArray jsonArray = new JSONArray();

        if (myListModel != null) {
            for (MyListModel listModel : myListModel) {
                PlaceModel placeModel = placeRepository.findById(listModel.getPlaceId());
                JSONObject object = new JSONObject();
                object.put("id", placeModel.getId());
                object.put("name", placeModel.getName());
                if(placeModel.getArea()!=null) {
                    object.put("area", placeModel.getArea());
                };
                object.put("subCategory", placeModel.getSubCategory());
                object.put("content", listModel.getContent());
                object.put("text", listModel.getText());
                if(listModel.getFileName()!=null) {
                    object.put("fileId", listModel.getFileId());
                    object.put("fileName", listModel.getFileName());
                }
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }

    public void putMyList(long userId, ListDto listDto) {
        MyListModel myListModel = myListRepository.findByUserIdAndPlaceId(userId, listDto.getPlaceId());
        if (myListModel == null) {
            myListModel = new MyListModel();
            myListModel.setUserId(userId);
            myListModel.setPlaceId(listDto.getPlaceId());
            myListModel.setContent(listDto.getContent());
            myListModel.setText(listDto.getText());
            myListRepository.save(myListModel);
        } else {
            myListModel.setContent(listDto.getContent());
            myListModel.setText(listDto.getText());
            myListRepository.save(myListModel);
        }
    }

    public void deletetMyList(long userId, long placeId) {
        MyListModel myListModel = myListRepository.findByUserIdAndPlaceId(userId, placeId);
        if (myListModel != null) {
            myListRepository.save(myListModel);
        }
    }
}
