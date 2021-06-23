package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.FileSecurity;
import com.example.yhwasongtest.place.model.*;
import com.example.yhwasongtest.place.repository.*;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PlaceService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    private PlaceRepository placeRepository;
    private LocationRepository locationRepository;
    private FoodRepository foodRepository;
    private DessertRepository dessertRepository;
    private PictureRepository pictureRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository,
                        LocationRepository locationRepository,
                        FoodRepository foodRepository,
                        DessertRepository dessertRepository,
                        PictureRepository pictureRepository) {
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;
        this.foodRepository = foodRepository;
        this.dessertRepository = dessertRepository;
        this.pictureRepository = pictureRepository;
    }

    public List<PlaceModel> getPlace() {
        List<PlaceModel> placeModelList = this.placeRepository.findByViewAndSubCategory();
        return placeModelList;
    }

    public PlaceModel getPlaceByName(String name) {
        return this.placeRepository.findByName(name);
    }

    public List<PlaceModel> getPlaceListBySubCategory(String category) {
        List<PlaceModel> placeModelList;
        if (category.equals("all")) {
            placeModelList = this.placeRepository.findByViewAndSubCategory();
        } else {
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

        // 이름이 영어면 저장 x
        String pattern = "/^[a-zA-Z]*$/";
        boolean isMatch = Pattern.matches(pattern, placeModel.getName());
        if (isMatch) return null;

        PlaceModel existPlace = placeRepository.findByName(placeModel.getName());
        if (existPlace == null) {
            existPlace = new PlaceModel();

            existPlace.setName(placeModel.getName());
            existPlace.setSubCategory("etc");
            existPlace.setView(1);
            existPlace.setYoutubes(placeModel.getYoutubes());

            if (placeModel.getRecommend() > 0) placeModel.setRecommend(0);
            else existPlace.setRecommend(placeModel.getRecommend());
            if (placeModel.getArea() == null) existPlace.setArea("");
            else existPlace.setArea(placeModel.getArea());
            if (placeModel.getNumber() == null) existPlace.setNumber("");
            else existPlace.setNumber(placeModel.getNumber());
            if (placeModel.getUrl() == null) existPlace.setUrl("");
            else existPlace.setUrl(placeModel.getUrl());
        } else {
            // 있는 장소라면 view +1
            existPlace.setYoutubes(placeModel.getYoutubes());
            existPlace.setView(existPlace.getView() + 1);
        }

        placeRepository.save(existPlace);

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

    public long saveFile(MultipartFile file) throws Exception {
        String orgname = file.getOriginalFilename();
        String filename = new FileSecurity().md5(orgname);

        Path rootpath = Paths.get("upload-dir");
        Path path = rootpath.resolve(filename); // 고정된 루트 경로에 부분경로 추가
        String savePath = path.toString();

        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        String filePath = savePath + "\\" + filename;
        file.transferTo(new File(filePath));

        PictureModel pictureModel = new PictureModel();
        pictureModel.setOriginFileName(orgname);
        pictureModel.setFileName(filename);
        pictureModel.setFilePath(filePath);
        pictureRepository.save(pictureModel);

        pictureModel = pictureRepository.findByOriginFileName(orgname);
        return pictureModel.getId();
    }

    public Resource loadFile(String filename) {
        try {
            Path rootpath = Paths.get("upload-dir");
            Path path = rootpath.resolve(filename); // 고정된 루트 경로에 부분경로 추가
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 영어 이름 제거
    public void deletePlaceContaingEng() {
        List<PlaceModel> places = placeRepository.findByNameContaingEng();
        for (PlaceModel place : places) {
            placeRepository.delete(place);
        }
    }

    // 유투브 중복인것 제거
    public void deletePlaceYoutube() {
        List<PlaceModel> places = placeRepository.findAll();
        for (PlaceModel place : places) {
            List<YoutubeModel> youtubeModels = place.getYoutubes();

            if (youtubeModels != null && youtubeModels.size() > 1) {
                HashSet<YoutubeModel> set = new HashSet<YoutubeModel>();
                for (int t = 0; t < youtubeModels.size(); t++) {
                    set.add(youtubeModels.get(t));
                }
                youtubeModels.clear();
                Iterator iter = set.iterator();    // Iterator 사용
                while (iter.hasNext()) {//값이 있으면 true 없으면 false
                    youtubeModels.add((YoutubeModel) iter.next());
                }
                placeRepository.save(place);
            }
        }
    }

    public List<PlaceModel> searchPlace(String msg) {

        List<PlaceModel> title_places = placeRepository.findByChannelTitle(msg);
        List<PlaceModel> name_places = placeRepository.findByNameContaing(msg);

        List<PlaceModel> places = new ArrayList<PlaceModel>();
        name_places.forEach(placeModel -> {
            if (!places.contains(placeModel))
                places.add(placeModel);
        });
        title_places.forEach(placeModel -> {
            if (!places.contains(placeModel))
                places.add(placeModel);
        });


        return places;
    }

}
