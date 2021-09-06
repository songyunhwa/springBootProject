package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.MyListModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MyListRepository extends JpaRepository<MyListModel , Long> {
    MyListModel findByUserIdAndPlaceId(long userId, long placeId);
    List<MyListModel> findByUserId(long userId);
}
