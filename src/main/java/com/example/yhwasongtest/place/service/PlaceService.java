package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.CategoryModel;
import com.example.yhwasongtest.place.model.LocationModel;
import com.example.yhwasongtest.place.repository.CategoryRepository;
import com.example.yhwasongtest.place.repository.LocationRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PlaceService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    private PlaceRepository placeRepository;
    private ReviewRepository reviewRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, ReviewRepository reviewRepository,
                        CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    public PlaceModel getPlace(String name) {
        return this.placeRepository.findByName(name);
    }

    public List<PlaceModel> getPlaceListBySubCategory(String category) {
        return this.placeRepository.findBySubCategory(category);
    }

    public PlaceModel putPlace(PlaceModel placeModel) throws Exception {
        if(placeModel.getSubCategory() == null){
            throw new Exception("sub_category 가 없습니다.");
        }
        if(placeModel.getName() == null){
            throw new Exception("name 이 없습니다.");
        }
        PlaceModel existPlace = placeRepository.findByName(placeModel.getName());
        if(existPlace == null)
            existPlace = new PlaceModel();

        existPlace.setName(placeModel.getName());
        existPlace.setSubCategory(placeModel.getSubCategory());

        if(placeModel.getView()>0) placeModel.setView(0);
        else existPlace.setView(placeModel.getView());
        if(placeModel.getRecommend()>0) placeModel.setRecommend(0);
        else existPlace.setRecommend(placeModel.getRecommend());
        if(placeModel.getArea()==null) existPlace.setArea("");
        else existPlace.setArea(placeModel.getArea());
        if(placeModel.getNumber()==null) existPlace.setNumber("");
        else existPlace.setNumber(placeModel.getNumber());
        if(placeModel.getUrl()==null) existPlace.setUrl("");
        else existPlace.setUrl(placeModel.getUrl());

        placeRepository.save(existPlace);

        return existPlace;
    }

    public void deletePlace(String name) {
        PlaceModel placeModel = placeRepository.findByName(name);
        placeRepository.delete(placeModel);
    }

    public ReviewModel putReview(ReviewModel review) throws Exception{
        if( review.getUserId() == 0 || review.getPlaceId() == 0 ) {
            throw new Exception("Request 값이 없습니다.");
        }
        ReviewModel reviewModel = reviewRepository.findByUserIdAndPlaceId(review.getUserId(), review.getPlaceId());
        if (reviewModel == null)
            return reviewRepository.save(review);
        else {
            reviewModel.setContents(review.getContents());
            reviewModel.setStar(review.getStar());
            return review;
        }
    }

    public void deleteReview(String userName, String placeName) {
        ReviewModel reviewModel = reviewRepository.findByUserNameAndPlaceName(userName, placeName);
        reviewRepository.delete(reviewModel);
    }

    // 서울 구/ 동 구분해서 => location table 저장
    public String getLocation() {
        try{
            //파일 객체 생성
            File rootfile = new File(".");
            String rootPath = rootfile.getAbsolutePath();
            String filePath = rootPath + "/src/main/java/com/example/yhwasongtest/place/txt/seoul_location.txt";
            File file = new File(filePath);
            String result = "";
            if(file.exists()) {
                BufferedReader inFile = new BufferedReader(new FileReader(file));
                String sLine = null;

                while ((sLine = inFile.readLine()) != null) {
                    if(!sLine.equals("")) {
                        String[] line = sLine.split(",");
                        LocationModel location = new LocationModel();
                        location.setCity("서울특별시");
                        location.setDistrict(line[0]);
                        location.setNeighborhood(line[1]);
                        locationRepository.save(location);
                    }
                    result += sLine; //읽어들인 문자열을 출력 합니다.
                }
                return result;
            }
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }

    public void addRecommend(String placeName){
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setRecommend(placeModel.getRecommend()+1);
        placeRepository.save(placeModel);

    }

    public void addView(String placeName){
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setView(placeModel.getView()+1);
        placeRepository.save(placeModel);

    }

}
