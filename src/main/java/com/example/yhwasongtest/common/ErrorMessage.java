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
    SIGNUP_INVALID(400,  "이메일과 비밀번호가 유효하지 않습니다"),
    SIGNUP_PWD_INVALID(400,  "비밀번호가 틀렸습니다."),
    SIGNUP_EMAIL_INVALID(400,  "등록된 이메일이 없습니다."),
    PUT_PLACE_INVALID(400,  "관리자로 로그인해주세요."),
    SIGNUP_GOOGLE_INVALID(400,  "등록된 사용자가 있습니다."),
    SIGNUP_GOOGLE_PREV_INVALID(400,  "구글로 등록된 사용자가 있습니다."),
    PUT_FILE_INVALID(400, "등록할 파일이 존재하지 않습니다.");

    int code;
    String message;


    ErrorMessage(int code,  String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
