package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.PictureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureModel, Long> {
    PictureModel findByFileName(String fileName);
    PictureModel findById(long id);
    PictureModel findByOriginFileName(String originFileName);
}
