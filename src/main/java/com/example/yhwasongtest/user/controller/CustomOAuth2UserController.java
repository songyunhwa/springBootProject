package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.common.OAuthAttributes;
import com.example.yhwasongtest.user.model.CustomOAuth2User;
import com.example.yhwasongtest.user.model.CustomOAuth2UserResponse;
import com.example.yhwasongtest.user.service.CustomOAuth2UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Map;


@RestController
public class CustomOAuth2UserController {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Value(value = "${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    @Value(value = "${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

/*
    @GetMapping(value = "/auth/google/callback")
    public ResponseEntity getLoginGoogle(
             @RequestParam(name = "state", required = true) String state
            , @RequestParam(name = "code", required = true) String code
            , @RequestParam(name = "authuser", required = true) String authuser
            , @RequestParam(name = "prompt", required = true) String prompt
            ,HttpServletRequest request, HttpServletResponse response) {

        String authCode = code;

        return new ResponseEntity(HttpStatus.OK);
    }
Model model, @RequestParam(value = "code") String authCode)
 */
    @GetMapping("/auth/google/callback")
    public String googleAuth(
            @RequestParam(name = "state", required = true) String state
            , @RequestParam(name = "code", required = true) String code
            , @RequestParam(name = "scope", required = true) String scope
            , @RequestParam(name = "authuser", required = true) String authuser
            , @RequestParam(name = "prompt", required = true) String prompt)
            throws JsonProcessingException {
        //https://gdtbgl93.tistory.com/182 참고
        //HTTP Request를 위한 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("redirect_uri", "http://localhost:9000/login/google/auth");
        parameters.add("grant_type", "authorization_code");
        parameters.add("response_type", "token");
        parameters.add("state", state);


        HttpEntity<MultiValueMap<String,String>> rest_request = new HttpEntity<>(parameters,headers);

        URI uri = URI.create("http://accounts.google.com/o/oauth2/v2/auth");

        ResponseEntity<String> rest_reponse;
        rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
        String bodys = rest_reponse.getBody();
        System.out.println(bodys);

        /*
        //Google OAuth Access Token 요청을 위한 파라미터 세팅
        CustomOAuth2User googleOAuthRequestParam = CustomOAuth2User
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri("http://localhost:9000/login/google/auth")
                .grantType("authorization_code")
                .build();


        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //AccessToken 발급 요청
        ResponseEntity<String> resultEntity = restTemplate.postForEntity("http://accounts.google.com/o/oauth2/v2/auth", googleOAuthRequestParam, String.class);

*/
        //Token Request
        //CustomOAuth2UserResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<CustomOAuth2UserResponse>() {});



        //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
        //String jwtToken = result.getIdToken();
        //String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
        //        .queryParam("id_token", jwtToken).encode().toUriString();

        //String resultJson = restTemplate.getForObject(requestUrl, String.class);

        //Map<String,String> userInfo = mapper.readValue(resultJson);
        //model.addAllAttributes(userInfo);
        //model.addAttribute("token", result.getAccessToken());

        return "/google.html";
    }
}
