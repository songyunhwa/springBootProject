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
    private CategoryRepository categoryRepository;
    private DessertRepository dessertRepository;

    @Autowired
    public void RecommendService(PlaceRepository placeRepository,
                                 RecommendRepository recommendRepository,
                                 WishedRepository wishedRepository,
                                 CategoryRepository categoryRepository,
                                 DessertRepository dessertRepository) {
        this.placeRepository = placeRepository;
        this.recommendRepository = recommendRepository;
        this.wishedRepository = wishedRepository;
        this.categoryRepository = categoryRepository;
        this.dessertRepository = dessertRepository;
    }


    public JSONArray getRecommend(String userName) throws Exception {
        ArrayList<PointDto> maps = new ArrayList<PointDto>();
        List<DessertModel> desserts = dessertRepository.findAll();

        desserts.forEach(dessert -> maps.add(new PointDto(dessert.getSubCategory(), 0)));


        // 찜에 들어있는 카테고리 별로 점수 부여
        WishedModel wishedModel = wishedRepository.findByUserName(userName);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(wishedModel.getPlaces());
        JSONArray arr = (JSONArray) obj;

        ArrayList<PlaceModel> placeModels = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = (JSONObject) arr.get(i);
            String place = jsonObject.get("place").toString();
            // 사용자가 찜한 place
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

    public String putRecommend(String userName, long id) throws Exception {
        RecommendModel recommendModel = recommendRepository.findByPlaceId(id);
        PlaceModel place = placeRepository.findById(id);
        JSONArray jsonArray = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("user", userName);
        boolean isRemove = false;

        if (recommendModel != null) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(recommendModel.getUsers());
            jsonArray = (JSONArray) obj;
            if (jsonArray.size() == 0) {
                jsonArray.add(object);
            } else {
                if(jsonArray.contains(object)){
                        jsonArray.remove(object);
                        isRemove = true;
                }else {
                        jsonArray.add(object);
                }
            }

        } else {
            recommendModel = new RecommendModel();
            recommendModel.setPlaceId(id);
            jsonArray.add(object);
        }

        recommendModel.setUsers(jsonArray.toJSONString());
        recommendRepository.save(recommendModel);

        // place에 추천수 집어넣기
        place.setRecommend(jsonArray.size());
        placeRepository.save(place);

        if(isRemove) {
            return "추천을 삭제했습니다.";
        }else {
            return "추천을 성공했습니다.";
        }
    }

    public JSONArray getWished(String userName) throws Exception {
        WishedModel wishedModel = wishedRepository.findByUserName(userName);

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

    public String putWished(String userName, long id) throws Exception {
        PlaceModel place = placeRepository.findById(id);
        WishedModel wishedModel = wishedRepository.findByUserName(userName);
        JSONArray jsonArray = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("place", place.getName());
        boolean isRemove = false;
        if (wishedModel != null) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(wishedModel.getPlaces());
            jsonArray = (JSONArray) obj;
            if(jsonArray.contains(object)){
                jsonArray.remove(object);
                isRemove = true;
            }else {
                jsonArray.add(object);
            }
        } else {
            wishedModel = new WishedModel();
            wishedModel.setUserName(userName);
            jsonArray.add(object);
        }

        wishedModel.setPlaces(jsonArray.toString());
        wishedRepository.save(wishedModel);

        if(isRemove) {
            return "찜을 삭제했습니다.";
        }else {
            return "찜을 성공했습니다.";
        }
    }


}
