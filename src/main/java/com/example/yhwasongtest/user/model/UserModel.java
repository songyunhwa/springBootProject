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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", uniqueConstraints=@UniqueConstraint(columnNames = {"username"}))
public class UserModel {
        @Id
        @GeneratedValue
        private Long id;
        private String username;    // 이메일
        private String password;    // 패스워드
        private String role;        // 권한
        private String sessionId;   // 세션 아이디
        private Date date;          // 현재 시간 => 세션과 쿠키 유효기간에 쓰임

        public UserModel(String email, String password, String role){
                this.username = email;
                this.password = password;
                this.role = role;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String email) {
                this.username = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public String getSessionId() {
                return sessionId;
        }

        public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
        }
}
