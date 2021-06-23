package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.RecommendModel;
import com.example.yhwasongtest.place.model.WishedModel;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.RecommendRepository;
import com.example.yhwasongtest.place.repository.WishedRepository;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendService {

    private PlaceRepository placeRepository;
    private RecommendRepository recommendRepository;
    private WishedRepository wishedRepository;

    @Autowired
    public void RecommendService(PlaceRepository placeRepository,
                                 RecommendRepository recommendRepository,
                                 WishedRepository wishedRepository) {
        this.placeRepository = placeRepository;
        this.recommendRepository = recommendRepository;
        this.wishedRepository = wishedRepository;
    }

    public void addRecommend(String placeName) {
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setRecommend(placeModel.getRecommend() + 1);
        placeRepository.save(placeModel);

    }

    public void putRecommend(String userName, long id) {
        RecommendModel recommendModel = recommendRepository.findByPlaceId(id);
        PlaceModel place = placeRepository.findById(id);
        List<String> users = new ArrayList<>();

        if (recommendModel != null) {
            users = recommendModel.getUserList();
            if (users.contains(userName)) {
                users.remove(userName);
            } else users.add(userName);
            recommendModel.setUserList(users);
        } else {
            recommendModel = new RecommendModel();
            recommendModel.setPlaceId(place.getId());
            users.add(userName);
            recommendModel.setUserList(users);

        }
        recommendRepository.save(recommendModel);

        // place에 추천수 집어넣기
        PlaceModel placeModel = place;
        placeModel.setRecommend(users.size());
        placeRepository.save(placeModel);
    }

    public JSONArray getWished(String userName) throws Exception {
        WishedModel wishedModel = wishedRepository.findByUserName(userName);
        JSONArray jsonArray = new JSONArray();

        if (wishedModel.getPlaces() != null) {
            String[] places = wishedModel.getPlaces().split(",");
            ArrayList<PlaceModel> placeModels= new ArrayList<>();
            for (String place : places) {
                PlaceModel placeModel = placeRepository.findByName(place);
                if (placeModel != null) {
                    placeModels.add(placeModel);
                }
            }
            jsonArray = CommonCode.convertToJSON(placeModels);
        }

        return jsonArray;

    }

    public void putWished(String userName, long id) {
        PlaceModel place = placeRepository.findById(id);
        WishedModel wishedModel = wishedRepository.findByUserName(userName);
        String places = wishedModel.getPlaces();
        if (places != null) {
            int startIndex = places.indexOf("," + place.getName());
            int endIndex = startIndex + place.getName().length();
            if (startIndex > 0) { // 지우는거 구현 안됨
                places = places.substring(0, startIndex) + places.substring(endIndex);
            } else places += "," + place.getName();
            wishedModel.setPlaces(places);
        } else {
            wishedModel = new WishedModel();
            wishedModel.setUserName(userName);
            wishedModel.setPlaces("," + place.getName());
        }
        wishedRepository.save(wishedModel);
    }

}
