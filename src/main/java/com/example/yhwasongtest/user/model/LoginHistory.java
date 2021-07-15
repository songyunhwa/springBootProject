package com.example.yhwasongtest.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "login_history")
public class LoginHistory {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;    // 이메일
    private String ip;          // 로그인한 ip
    private String status;      // LOGIN or LOGOUT
    private Date loginDate;     // 로그인 시간
    private Date logoutDate;    // 로그아웃 시간
}
