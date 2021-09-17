package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.WishedModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WishedRepository extends JpaRepository<WishedModel, Long> {
    WishedModel findById(long id);
    List<WishedModel> findByUserId(long userId);
    WishedModel findByUserIdAndPlaceId(long userId, long placeId);
    Page<WishedModel> findByUserId(long userId, Pageable pageable);
}
