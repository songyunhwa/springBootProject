package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.PictureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureModel, Long> {
    PictureModel findByOriginFileName(String originFileName);
    PictureModel findById(long id);
}
