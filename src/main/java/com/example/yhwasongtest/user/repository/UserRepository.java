package com.example.yhwasongtest.user.repository;


import com.example.yhwasongtest.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findAllById(Long id);
    Optional<UserModel> findAllByName(String name);
    UserModel findByName(String name);
}
