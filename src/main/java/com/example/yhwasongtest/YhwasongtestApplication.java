package com.example.yhwasongtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Collections;

@SpringBootApplication
@EnableRedisHttpSession
public class YhwasongtestApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(YhwasongtestApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "9000"));
        app.run(args);

        //SpringApplication.run(YhwasongtestApplication.class, args);
    }

}
