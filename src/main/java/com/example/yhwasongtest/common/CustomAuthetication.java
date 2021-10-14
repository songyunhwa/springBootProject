package com.example.yhwasongtest.common;

import org.springframework.beans.factory.annotation.Value;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class CustomAuthetication extends Authenticator {
    @Value(value = "${google_id}")
    String google_id;

    @Value(value = "${google_password}")
    char[] google_password;


    PasswordAuthentication pa;

    public CustomAuthetication(){  //생성자를 통해 구글 ID/PW 인증

        pa = new PasswordAuthentication(google_id, google_password);
    }

    // 시스템에서 사용하는 인증정보
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }

}
