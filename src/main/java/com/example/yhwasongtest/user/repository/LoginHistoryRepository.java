package com.example.yhwasongtest.user.repository;

import com.example.yhwasongtest.user.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByUserNameAndLoginDateBetween(String userName, Date start, Date end);
    List<LoginHistory> findByLoginDateBetween(Date start, Date end);
}
