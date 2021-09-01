package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.user.model.CustomOAuth2User;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Date;


@RestController
public class CustomOAuth2UserController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomOAuth2UserController.class);

    @Value(value = "${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    @Value(value = "${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    @Value(value = "${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUri;

    private String userName;
    private String email;

    private final UserRepository userRepository;

    public CustomOAuth2UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/auth/google/callback")
    public ResponseEntity googleAuthCallback(@RequestParam(name = "code", required = true) String code)
            throws JsonProcessingException {
        //https://gdtbgl93.tistory.com/182 참고
        //HTTP Request를 위한 RestTemplate
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=utf-8"));

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("code", code);
            parameters.add("client_id", clientId);
            parameters.add("client_secret", clientSecret);
            parameters.add("redirect_uri", redirectUri);
            parameters.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> rest_request = new HttpEntity<>(parameters, headers);

            URI uri = URI.create("https://accounts.google.com/o/oauth2/token");

            ResponseEntity<String> rest_reponse;
            rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
            String bodys = rest_reponse.getBody();

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(bodys);
            JSONObject jsonObj = (JSONObject) obj;

            String access_token = (String) jsonObj.get("access_token");
            //String id_token = (String)jsonObj.get("id_token");

            // 회원가입
            UserModel userModel = googleAuthCheck(access_token);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", userModel.getUsername());
            jsonObject.put("role", userModel.getRole());
            jsonArray.add(jsonObject);

            return new ResponseEntity(jsonArray.toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void googleAuthProfile(String access_token) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=utf-8"));

        URI uri = URI.create("https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + access_token);
        ResponseEntity<String> rest_reponse;
        rest_reponse = restTemplate.getForEntity(uri, String.class);
        String bodys = rest_reponse.getBody();

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(bodys);
        JSONObject jsonObj = (JSONObject) obj;


        userName = (String) jsonObj.get("name");
        email = (String) jsonObj.get("email");
    }

    @GetMapping("/auth/google/check")
    public UserModel googleAuthCheck(String access_token) throws Exception {

        googleAuthProfile(access_token);

        UserModel userModel = userRepository.findByUsernameAndEmail(userName, email);
        if (userModel == null) {

            userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setEmail(email);
            userModel.setRole("ROLE_USER");
            userModel.setDate(new Date());
            userRepository.save(userModel);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("login", userModel);
        session.setMaxInactiveInterval(1800); //30분

        return userModel;
    }

}
