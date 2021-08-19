package com.example.yhwasongtest.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    // 로그인 화면
    NOT_LOGIN_INVALID(400, " 로그인이 되지 않았습니다."),
    EMAIL_DUPLICATION(400, "등록된 이메일이 있습니다."),
    SIGNUP_PWD_INVALID(400,  "비밀번호가 틀렸습니다."),
    SIGNUP_EMAIL_INVALID(400,  "등록된 이메일이 없습니다.");

    int code;
    String message;


    ErrorMessage(int code,  String message) {
        this.code = code;
        this.message = message;
    }

}
