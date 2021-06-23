package com.example.yhwasongtest.common;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommonCode {

    public static JSONArray convertToJSON(List<PlaceModel> placeModels) throws Exception{
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
                subArray.put(subObject);
            }
            jsonObject.put("youtube", subArray);


            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
