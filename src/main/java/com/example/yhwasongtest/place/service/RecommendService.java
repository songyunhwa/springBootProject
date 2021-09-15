package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.dto.PointDto;
import com.example.yhwasongtest.place.model.*;
import com.example.yhwasongtest.place.repository.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecommendService {

    private PlaceRepository placeRepository;
    private RecommendRepository recommendRepository;
    private WishedRepository wishedRepository;
    private DessertRepository dessertRepository;

    @Autowired
    public void RecommendService(PlaceRepository placeRepository,
                                 RecommendRepository recommendRepository,
                                 WishedRepository wishedRepository,
                                 DessertRepository dessertRepository) {
        this.placeRepository = placeRepository;
        this.recommendRepository = recommendRepository;
        this.wishedRepository = wishedRepository;
        this.dessertRepository = dessertRepository;
    }


    public JSONArray getRecommend(long userId) throws Exception {
        ArrayList<PointDto> maps = new ArrayList<PointDto>();
        List<DessertModel> desserts = dessertRepository.findAll();

        desserts.forEach(dessert -> maps.add(new PointDto(dessert.getSubCategory(), 0)));

        // 사용자가 찜한 place
        ArrayList<PlaceModel> placeModels = new ArrayList<>();

        if (userId > -1) {
            // 찜에 들어있는 카테고리 별로 점수 부여
            WishedModel wishedModel = wishedRepository.findByUserId(userId);

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(wishedModel.getPlaces());
            JSONArray arr = (JSONArray) obj;

            for (int i = 0; i < arr.size(); i++) {
                JSONObject jsonObject = (JSONObject) arr.get(i);
                String place = jsonObject.get("place").toString();

                PlaceModel placeModel = placeRepository.findByName(place);
                placeModels.add(placeModel);

                if (placeModel != null) {
                    String subCategory = placeModel.getSubCategory();
                    // 카테고리 명에 따라 점수 추가
                    maps.forEach(map -> {
                        if (map.category.equals(subCategory)) {
                            map.point = map.point + 1;
                        }
                    });

                }
            }
        }
        // 카테고리 점수가 높은 순대로 결과에 추가
        Collections.sort(maps);

        List<PlaceModel> result = new ArrayList<PlaceModel>();
        for (PointDto p : maps) {
            if (result.size() > 20) break;

            List<PlaceModel> placeModel = placeRepository.findBySubCategoryOrderByRecommendDecsViewDesc(p.category);
            result.addAll(placeModel.stream().filter(place -> !placeModels.contains(place)).collect(Collectors.toList()));
        }
        JSONArray jsonArray = CommonCode.convertToJSON(result);
        return jsonArray;
    }

    public String putRecommend(long userId, long placeId) throws Exception {
        RecommendModel recommendModel = recommendRepository.findByPlaceId(placeId);
        PlaceModel place = placeRepository.findById(placeId);
        JSONArray jsonArray = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("user", userId);
        boolean isRemove = false;

        if (recommendModel != null) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(recommendModel.getUsers());
            jsonArray = (JSONArray) obj;
            if (jsonArray.size() == 0) {
                jsonArray.add(object);
            } else {
                if (jsonArray.contains(object)) {
                    jsonArray.remove(object);
                    isRemove = true;
                } else {
                    jsonArray.add(object);
                }
            }

        } else {
            recommendModel = new RecommendModel();
            recommendModel.setPlaceId(placeId);
            jsonArray.add(object);
        }

        recommendModel.setUsers(jsonArray.toJSONString());
        recommendRepository.save(recommendModel);

        // place에 추천수 집어넣기
        place.setRecommend(jsonArray.size());
        placeRepository.save(place);

        if (isRemove) {
            return "추천을 삭제했습니다.";
        } else {
            return "추천을 성공했습니다.";
        }
    }

    public JSONArray getWished(long userId) throws Exception {
        WishedModel wishedModel = wishedRepository.findByUserId(userId);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(wishedModel.getPlaces());
        JSONArray arr = (JSONArray) obj;

        ArrayList<PlaceModel> placeModels = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = (JSONObject) arr.get(i);
            String place = jsonObject.get("place").toString();
            PlaceModel placeModel = placeRepository.findByName(place);
            if (placeModel != null) {
                placeModels.add(placeModel);
            }
        }
        JSONArray jsonArray = CommonCode.convertToJSON(placeModels);


        return jsonArray;

    }

    public String putWished(long userId, long placeId) throws Exception {
        PlaceModel place = placeRepository.findById(placeId);
        WishedModel wishedModel = wishedRepository.findByUserId(userId);
        JSONArray jsonArray = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("place", place.getName());
        boolean isRemove = false;
        if (wishedModel != null) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(wishedModel.getPlaces());
            jsonArray = (JSONArray) obj;
            if (jsonArray.contains(object)) {
                jsonArray.remove(object);
                isRemove = true;
            } else {
                jsonArray.add(object);
            }
        } else {
            wishedModel = new WishedModel();
            wishedModel.setUserId(userId);
            jsonArray.add(object);
        }

        wishedModel.setPlaces(jsonArray.toString());
        wishedRepository.save(wishedModel);

        if (isRemove) {
            return "찜을 삭제했습니다.";
        } else {
            return "찜을 성공했습니다.";
        }
    }

}
