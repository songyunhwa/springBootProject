package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.PictureModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<PictureModel, Long> {
    PictureModel findByFileName(String fileName);
    PictureModel findById(long id);
    PictureModel findByReviewId(long reviewId);

    List<PictureModel> findByListId(long listId);
    List<PictureModel> findByOriginFileName(String originFileName);
}
