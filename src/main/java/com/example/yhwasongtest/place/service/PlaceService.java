package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.*;
import com.example.yhwasongtest.place.repository.*;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PlaceService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    private PlaceRepository placeRepository;
    private ReviewRepository reviewRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;
    private FoodRepository foodRepository;
    private DessertRepository dessertRepository;
    private RecommendRepository recommendRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository,
                        ReviewRepository reviewRepository,
                        CategoryRepository categoryRepository,
                        LocationRepository locationRepository,
                        FoodRepository foodRepository,
                        DessertRepository dessertRepository,
                        RecommendRepository recommendRepository) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.foodRepository = foodRepository;
        this.dessertRepository = dessertRepository;
        this.recommendRepository = recommendRepository;
    }

    public PlaceModel getPlace(String name) {
        return this.placeRepository.findByName(name);
    }

    public List<PlaceModel> getPlaceListBySubCategory(String category) {
        List<PlaceModel> placeModelList;
        if(category.equals("all")){
            placeModelList = this.placeRepository.findByExceptEtcSubCategory();
        }else {
            placeModelList = this.placeRepository.findBySubCategoryOrderByViewDesc(category);
        }
        return placeModelList;
    }

    public PlaceModel putPlace(PlaceModel placeModel) throws Exception {
        if (placeModel.getSubCategory() == null) {
            throw new Exception("sub_category 가 없습니다.");
        }
        if (placeModel.getName() == null) {
            throw new Exception("name 이 없습니다.");
        }
        if (placeModel.getYoutube().size() == 0) {
            throw new Exception("youtube 가 없습니다.");
        }

        // 이름이 영어면 저장 x
        String pattern = "/^[a-zA-Z]*$/";
        boolean isMatch = Pattern.matches(pattern, placeModel.getName());
        if (isMatch) return null;

        PlaceModel existPlace = placeRepository.findByName(placeModel.getName());
        if (existPlace == null) {
            existPlace = new PlaceModel();

            existPlace.setName(placeModel.getName());
            existPlace.setSubCategory("etc");
            List<YoutubeModel> youtubeModels = placeModel.getYoutube();
            existPlace.setYoutube(youtubeModels);
            existPlace.setView(1);

            if (placeModel.getRecommend() > 0) placeModel.setRecommend(0);
            else existPlace.setRecommend(placeModel.getRecommend());
            if (placeModel.getArea() == null) existPlace.setArea("");
            else existPlace.setArea(placeModel.getArea());
            if (placeModel.getNumber() == null) existPlace.setNumber("");
            else existPlace.setNumber(placeModel.getNumber());
            if (placeModel.getUrl() == null) existPlace.setUrl("");
            else existPlace.setUrl(placeModel.getUrl());

            placeRepository.save(existPlace);
        } else {
            // 있는 장소라면 view +1
            existPlace.setView(existPlace.getView() + 1);
            List<YoutubeModel> existYoutubes = existPlace.getYoutube();
            existYoutubes.add(placeModel.getYoutube().get(0));
            placeRepository.save(existPlace);
        }

        return existPlace;
    }

    public void deletePlace(String name) {
        PlaceModel placeModel = placeRepository.findByName(name);
        placeRepository.delete(placeModel);
    }

    // 서울 구/ 동 구분해서 => location table 저장
    public String getLocation() {
        try {
            //파일 객체 생성
            File rootfile = new File(".");
            String rootPath = rootfile.getAbsolutePath();
            String filePath = rootPath + "/src/main/java/com/example/yhwasongtest/place/txt/seoul_location.txt";
            File file = new File(filePath);
            String result = "";
            if (file.exists()) {
                BufferedReader inFile = new BufferedReader(new FileReader(file));
                String sLine = null;

                while ((sLine = inFile.readLine()) != null) {
                    if (!sLine.equals("")) {
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
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public void addView(String placeName) {
        PlaceModel placeModel = placeRepository.findByName(placeName);
        placeModel.setView(placeModel.getView() + 1);
        placeRepository.save(placeModel);

    }

    // place 카테고리별로 분류하기
    public void getFoodCategory() {

        List<FoodModel> foodModelList = foodRepository.findAll();
        List<PlaceModel> placeModelList;
        for (FoodModel foodModel : foodModelList) {
            placeModelList = placeRepository.findByNameContaining(foodModel.getIncluded());
            for (PlaceModel placeModel : placeModelList) {
                if (!placeModel.getSubCategory().equals(foodModel.getSubCategory()))
                    placeModel.setSubCategory(foodModel.getSubCategory());
            }
            placeRepository.saveAll(placeModelList);
        }

        placeModelList = placeRepository.findBySubCategory("food");
        placeRepository.deleteAll(placeModelList);
    }

    public void getDessertCategory() {

        List<DessertModel> dessertModelList = dessertRepository.findAll();
        List<PlaceModel> placeModelList;
        for (DessertModel dessertModel : dessertModelList) {
            placeModelList = placeRepository.findByNameContaining(dessertModel.getIncluded());
            for (PlaceModel placeModel : placeModelList) {
                if (!placeModel.getSubCategory().equals(dessertModel.getSubCategory()))
                    placeModel.setSubCategory(dessertModel.getSubCategory());
            }
            placeRepository.saveAll(placeModelList);
        }
    }


}
