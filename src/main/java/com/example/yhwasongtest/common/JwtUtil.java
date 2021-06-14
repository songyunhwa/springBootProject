package com.example.yhwasongtest.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {


    public JwtUtil() {


    }

    public static String createToken(Long id, String name) {
        Properties properties = new Properties();

        byte[] key = properties.getProperty("token.secret").getBytes();

        String token = Jwts.builder()
                .claim("userId", id)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return token;
    }
}
