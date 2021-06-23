package com.example.yhwasongtest.place.repository;

import com.example.yhwasongtest.place.model.WishedModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishedRepository extends JpaRepository<WishedModel, Long> {
    WishedModel findByUserName(String userName);


    WishedModel findById(long id);
}
