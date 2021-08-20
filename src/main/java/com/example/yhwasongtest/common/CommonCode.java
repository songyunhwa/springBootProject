package com.example.yhwasongtest.common;

import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.DessertRepository;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommonCode {

    public static JSONArray convertToJSON(List<PlaceModel> placeModels) {
        JSONArray jsonArray = new JSONArray();
        for (PlaceModel placeModel : placeModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", placeModel.getId());
            jsonObject.put("name", placeModel.getName());
            jsonObject.put("area", placeModel.getArea());
            jsonObject.put("url", placeModel.getUrl());
            jsonObject.put("number", placeModel.getNumber());
            jsonObject.put("subCategory", placeModel.getSubCategory());
            jsonObject.put("recommend", placeModel.getRecommend());
            jsonObject.put("view", placeModel.getView());
            jsonObject.put("fileId", placeModel.getFileId());

            JSONArray subArray = new JSONArray();
            JSONObject subObject = new JSONObject();
            List<YoutubeModel> youtubes = new ArrayList<>();
            youtubes = placeModel.getYoutubes();
            for(YoutubeModel youtube : youtubes){
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

    public static JSONArray reviewConvertToJSON(List<ReviewModel> reviewModels) {
        JSONArray jsonArray = new JSONArray();
        for (ReviewModel reviewModel : reviewModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", reviewModel.getId());
            jsonObject.put("userName" , reviewModel.getUserName());
            jsonObject.put("userId" , reviewModel.getUserId());
            jsonObject.put("contents" , reviewModel.getContents());
            jsonObject.put("star" , reviewModel.getStar());
            jsonObject.put("prevId" , reviewModel.getPrevId());
            jsonObject.put("fileId", reviewModel.getFileId());
            jsonObject.put("fileName", reviewModel.getFileName());
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
