package com.example.yhwasongtest.user.repository;

import com.example.yhwasongtest.user.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    LoginHistory findByStatusAndUserNameAndLoginDateBetween(String status, String userName, Date start, Date end);
    List<LoginHistory> findByStatusAndLoginDateBetween(String status, Date start, Date end);
}
