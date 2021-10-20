package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.dto.ListDto;
import com.example.yhwasongtest.place.model.*;
import com.example.yhwasongtest.place.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

    private MyListRepository myListRepository;
    private PlaceRepository placeRepository;
    private PictureRepository pictureRepository;
    private LocationRepository locationRepository;
    private ReviewService reviewService;

    @Autowired
    public ListService(MyListRepository myListRepository, PlaceRepository placeRepository, PictureRepository pictureRepository,
                       ReviewService reviewService, LocationRepository locationRepository) {
        this.myListRepository = myListRepository;
        this.placeRepository = placeRepository;
        this.pictureRepository = pictureRepository;
        this.locationRepository = locationRepository;
        this.reviewService = reviewService;
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

                // 지역정보 가져오기
                List<LocationModel> locationModels = locationRepository.findByPlaceId(placeModel.getId());
                JSONArray array = new JSONArray();
                for (LocationModel locationModel : locationModels) {
                    JSONObject subObject = new JSONObject();
                    subObject.put("address", locationModel.getAddress());
                    subObject.put("lat", locationModel.getLat());
                    subObject.put("lng", locationModel.getLng());
                    array.add(subObject);
                }
                object.put("location", array);


                object.put("subCategory", placeModel.getSubCategory());
                object.put("content", listModel.getContent());
                object.put("text", listModel.getText());

                JSONArray files = new JSONArray();
                List<PictureModel> pictureModels = pictureRepository.findByListId(listModel.getId());
                if (pictureModels != null) {
                    for (PictureModel pictureModel : pictureModels) {
                        JSONObject file = new JSONObject();
                        file.put("fileId", pictureModel.getId());
                        file.put("fileName", pictureModel.getFileName());
                        files.add(file);
                    }
                }
                object.put("file", files);
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
        }

        myListModel.setContent(listDto.getContent());
        myListModel.setText(listDto.getText());
        myListRepository.save(myListModel);

        myListModel = myListRepository.findByUserIdAndPlaceId(userId, listDto.getPlaceId());

        /** 파일 수정 **/
        if (listDto.getFileId() != null) {
            List<PictureModel> pictureModels = pictureRepository.findByListId(myListModel.getId());
            for (PictureModel pictureModel : pictureModels) {
                if (!listDto.getFileId().contains(pictureModel.getId())) {
                    reviewService.deleteFile(pictureModel.getId());
                }
            }

            for (long id : listDto.getFileId()) {
                PictureModel pictureModel = pictureRepository.findById(id);
                pictureModel.setListId(myListModel.getId());
                pictureRepository.save(pictureModel);
            }
        }
    }

    public void deletetMyList(long userId, long placeId) {
        MyListModel myListModel = myListRepository.findByUserIdAndPlaceId(userId, placeId);
        if (myListModel != null) {
            List<PictureModel> pictureModels = pictureRepository.findByListId(myListModel.getId());
            if (pictureModels != null) {
                for (PictureModel pictureModel : pictureModels) {
                    pictureModel.setListId(-1);
                    pictureRepository.save(pictureModel);
                }
            }
            myListRepository.delete(myListModel);
        }
    }
}
