package com.example.yhwasongtest.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class LoginHistory {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;    // 이메일
    private String ip;          // 로그인한 ip
    private Date loginDate; // 로그인 시간
}
