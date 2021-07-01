package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.FileSecurity;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, PlaceRepository placeRepository, PictureRepository pictureRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.pictureRepository = pictureRepository;
    }

    public List<ReviewModel> getReview(long id){
        return reviewRepository.findByPlaceId(id);
    }

    public ReviewModel putReview(ReviewModel review) throws Exception {

        UserModel userModel = userRepository.findByUsername(review.getUserName());
        PlaceModel placeModel = placeRepository.findById(review.getPlaceId());

        if (userModel == null || placeModel == null) {
            throw new Exception("Request 값이 없습니다.");
        }

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setUserId(review.getUserId());
        reviewModel.setUserName(review.getUserName());
        reviewModel.setPlaceId(review.getPlaceId());
        reviewModel.setPlaceName(review.getPlaceName());
        reviewModel.setContents(review.getContents());
        reviewModel.setPrevId(review.getPrevId());
        reviewModel.setStar(0);
        reviewModel.setFileId(review.getFileId());
        reviewModel.setFileName(review.getFileName());

        return reviewRepository.save(reviewModel);
    }

    public void modifyReview(ReviewModel review){
        Optional<ReviewModel> reviewModelOptional = reviewRepository.findById(review.getId());
        ReviewModel reviewModel = reviewModelOptional.get();
        reviewModel.setContents(review.getContents());
        reviewModel.setStar(review.getStar());
        reviewModel.setFileId(review.getFileId());
        reviewModel.setFileName(review.getFileName());
        reviewRepository.save(reviewModel);
    }

    public void deleteReview(long id) {
        Optional<ReviewModel> reviewModel = reviewRepository.findById(id);
        reviewRepository.delete(reviewModel.get());
    }

    public PictureModel saveFile(MultipartFile file) throws Exception {
        String orgname = file.getOriginalFilename();
        String filename = new FileSecurity().md5(orgname);

        PictureModel pictureModel = pictureRepository.findByFileName(filename);
        if(pictureModel == null) {
            String root_path = "C:\\Users\\pc\\Documents\\공부\\springBootProject_back\\web\\vue\\src\\assets\\images\\";

            String savePath = root_path + filename + ".png";

            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            file.transferTo(new File(savePath));

            pictureModel = new PictureModel();
            pictureModel.setOriginFileName(orgname);
            pictureModel.setFileName(filename);
            pictureModel.setFilePath(savePath);
            pictureRepository.save(pictureModel);

            pictureModel = pictureRepository.findByFileName(filename);
        }

        return pictureModel;
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

}
