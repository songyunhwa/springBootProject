package com.example.yhwasongtest.user.repository;


import com.example.yhwasongtest.user.model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    //UserModel findAllById(Long id);
    Optional<UserModel> findAllByUsername(String username);
    UserModel findByUsername(String username);
    UserModel findBySessionId(String sessionId);
    UserModel findByUsernameAndEmail(String username, String email);
    UserModel findByEmail(String email);
}
