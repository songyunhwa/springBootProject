package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.FileSecurity;
import com.example.yhwasongtest.place.dto.ReviewDto;
import com.example.yhwasongtest.place.model.PictureModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.model.ReviewModel;
import com.example.yhwasongtest.place.repository.PictureRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.repository.ReviewRepository;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Value(value = "${image-path}")
    String root_path;

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

    public List<ReviewModel> getReview(long id) {
        return reviewRepository.findByPlaceId(id);

    }

    public ReviewModel putReview(ReviewDto review) throws Exception {

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
        /////reviewModel.setIsMyList(review.getIsMyList());

        reviewModel = reviewRepository.save(reviewModel);

        if (review.getFileId() > 0) {
            PictureModel pictureModel = pictureRepository.findById(review.getFileId());
            pictureModel.setReviewId(reviewModel.getId());
            pictureRepository.save(pictureModel);
        }

        return reviewModel;
    }

    public void modifyReview(ReviewDto review) {
        ReviewModel reviewModel = reviewRepository.findById(review.getId());
        reviewModel.setContents(review.getContents());
        reviewModel.setStar(review.getStar());

        reviewModel = reviewRepository.save(reviewModel);
        PictureModel pictureModel = pictureRepository.findByReviewId(review.getId());
        if (pictureModel != null) {
            pictureModel.setReviewId(-1);
            pictureRepository.save(pictureModel);
        }
        if (review.getFileId() > 0) {
            PictureModel pictureModel1 = pictureRepository.findById(review.getFileId());
            pictureModel.setReviewId(reviewModel.getId());
            pictureRepository.save(pictureModel1);
        }
    }

    public void deleteReview(long id) {
        ReviewModel reviewModel = reviewRepository.findById(id);

        PictureModel pictureModel = pictureRepository.findByReviewId(reviewModel.getId());
        if (pictureModel != null) {
            deleteFile(pictureModel.getId());
        }
        reviewRepository.delete(reviewModel);
    }

    public byte[] loadFile(String fileName, InputStream is) throws Exception {
        //String savePath = root_path + filename + ".png";
        String savePath = "C:\\Users\\pc\\Documents\\springBootProject_image\\" + fileName;
        File file = new File(savePath);

        FileInputStream fis = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];

        try {
            while ((readCount = fis.read(buffer)) != -1) {
                System.out.write(buffer, 0, readCount);
            }
            fis.close();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("File Error");
        }

        return buffer;
    }

    public PictureModel saveFile(MultipartFile file) throws Exception {
        String filename = "";

        String orgname = file.getOriginalFilename();
        List<PictureModel> pictures = pictureRepository.findByOriginFileName(orgname);
        if (pictures != null) {
            filename = orgname + "_" + pictures.size();
        }
        filename = new FileSecurity().md5(filename);
        filename += orgname.substring(orgname.lastIndexOf("."));

        String savePath = root_path + filename;
        if (pictures == null) {
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
        file.transferTo(new File(savePath));

        PictureModel pictureModel = new PictureModel();
        pictureModel.setOriginFileName(orgname);
        pictureModel.setFileName(filename);
        pictureModel.setFilePath(savePath);
        pictureRepository.save(pictureModel);
        return pictureRepository.findByFileName(filename);
    }

    public void deleteFile(long id) {

        PictureModel pictureModel = pictureRepository.findById(id);
        if (pictureModel != null) {
            String savePath = root_path + pictureModel.getFileName();
            File deleteFile = new File(savePath);
            if (deleteFile.exists()) {
                try {
                    deleteFile.delete();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            pictureRepository.delete(pictureModel);
        }
    }
}
