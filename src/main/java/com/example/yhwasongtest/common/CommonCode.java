package com.example.yhwasongtest.common;

import com.example.yhwasongtest.place.model.*;
import com.example.yhwasongtest.place.repository.LocationRepository;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CommonCode {

    private static LocationRepository locationRepository;

    @Autowired
    public void CommonCode(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public static JSONArray convertToJSON(List<PlaceModel> placeModels) {
        JSONArray jsonArray = new JSONArray();
        for (PlaceModel placeModel : placeModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", placeModel.getId());
            jsonObject.put("name", placeModel.getName());
            jsonObject.put("url", placeModel.getUrl());
            jsonObject.put("number", placeModel.getNumber());
            jsonObject.put("subCategory", placeModel.getSubCategory());
            jsonObject.put("recommend", placeModel.getRecommend());
            jsonObject.put("view", placeModel.getView());
            jsonObject.put("fileId", placeModel.getFileId());

            // 지역정보 가져오기
            List<LocationModel> locationModels = locationRepository.findByPlaceId(placeModel.getId());
            JSONArray array = new JSONArray();
            for (LocationModel locationModel : locationModels) {
                JSONObject object = new JSONObject();
                object.put("address" , locationModel.getAddress());
                object.put("lat" , locationModel.getLat());
                object.put("lng" , locationModel.getLng());
                array.add(object);
            }
            jsonObject.put("location",array);


            // 유투브 가져오기
            JSONArray subArray = new JSONArray();
            List<YoutubeModel> youtubes = new ArrayList<>();
            youtubes = placeModel.getYoutubes();
            for(YoutubeModel youtube : youtubes){
                JSONObject subObject = new JSONObject();
                subObject.put("videoId", youtube.getVideoId());
                subObject.put("channelTitle", youtube.getChannelTitle());
                subObject.put("title", youtube.getTitle());
                subArray.add(subObject);
            }
            jsonObject.put("youtube", subArray);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray categoryConvertToJSON(List<DessertModel> dessertModels) {
        JSONArray jsonArray = new JSONArray();
        for (DessertModel dessertModel : dessertModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", dessertModel.getId());
            jsonObject.put("subCategory" , dessertModel.getSubCategory());
            jsonObject.put("included" , dessertModel.getIncluded());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
