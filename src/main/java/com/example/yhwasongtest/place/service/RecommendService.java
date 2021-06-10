package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.RecommendModel;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendService {

    private PlaceRepository placeRepository;
    private RecommendRepository recommendRepository;

    @Autowired
    public void RecommendService(PlaceRepository placeRepository,
                                 RecommendRepository recommendRepository){
        this.placeRepository = placeRepository;
        this.recommendRepository = recommendRepository;
    }

    public void addRecommend(String placeName) {
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setRecommend(placeModel.getRecommend() + 1);
        placeRepository.save(placeModel);

    }

    public void putRecommend(String userName, String placeName) {
        RecommendModel recommendModel = recommendRepository.findByPlaceName(placeName);
        List<String> users = recommendModel.getUserList();
        if (users.contains(userName)) {
            users.remove(userName);
        } else users.add(userName);

        recommendModel.setUserList(users);
        recommendRepository.save(recommendModel);

        // place에 추천수 집어넣기
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setRecommend(users.size());
        placeRepository.save(placeModel);
    }
}
