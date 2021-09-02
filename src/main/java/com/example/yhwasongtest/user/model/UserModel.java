package com.example.yhwasongtest.user.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserModel {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;    // 패스워드

    private String role;        // 권한
    private String sessionId;   // 세션 아이디
    private Date date;          // 현재 시간 => 세션과 쿠키 유효기간에 쓰임

    public UserModel() {

    }

    public UserModel(String email, String password, String role) {
        this.username = email;
        this.password = password;
        this.role = role;
    }

    public UserModel(String email, String role) {
        this.username = email;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role_user) {
        this.role = role_user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Date getDate() {
        return date;
    }
}
