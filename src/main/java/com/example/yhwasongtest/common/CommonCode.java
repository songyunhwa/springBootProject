package com.example.yhwasongtest.common;

import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommonCode {

    static PictureRepository pictureRepository;

    @Autowired
    public void CommonCode(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

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
