package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.dto.ListDto;
import com.example.yhwasongtest.place.model.MyListModel;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.MyListRepository;
import com.example.yhwasongtest.place.repository.PictureRepository;
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
    private PictureRepository pictureRepository;
    private ReviewService reviewService;

    @Autowired
    public ListService(MyListRepository myListRepository, ReviewRepository reviewRepository, PlaceRepository placeRepository, PictureRepository pictureRepository,
                       ReviewService reviewService) {
        this.myListRepository = myListRepository;
        this.reviewRepository = reviewRepository;
        this.placeRepository = placeRepository;
        this.pictureRepository = pictureRepository;
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
                if(placeModel.getArea()!=null) {
                    object.put("area", placeModel.getArea());
                };
                object.put("subCategory", placeModel.getSubCategory());
                object.put("content", listModel.getContent());
                object.put("text", listModel.getText());

                JSONArray files = new JSONArray();
                List<PictureModel> pictureModels = pictureRepository.findByListId(listModel.getId());
                if (pictureModels != null) {
                    for(PictureModel pictureModel : pictureModels) {
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
        List<PictureModel> pictureModels= pictureRepository.findByListId(myListModel.getId());
        for (PictureModel pictureModel : pictureModels) {
            if (!listDto.getFileId().contains(pictureModel.getId())) {
                pictureRepository.delete(pictureModel);
            }
        }
        if (listDto.getFileId()!=null) {
            for(long id : listDto.getFileId()) {
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
            if (pictureModels!=null) {
                for (PictureModel pictureModel : pictureModels) {
                    pictureModel.setListId(-1);
                    pictureRepository.save(pictureModel);
                }
            }
            myListRepository.delete(myListModel);
        }
    }
}
