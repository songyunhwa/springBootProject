package com.example.yhwasongtest.repository;

import com.example.yhwasongtest.model.BaseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseRepository extends JpaRepository<BaseQuestion, Long> {
    List<BaseQuestion> findAllById(Long id);

}
