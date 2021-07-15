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

}
