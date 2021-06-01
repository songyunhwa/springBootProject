package com.example.yhwasongtest.user.service;

import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.BaseRepository;
import com.example.yhwasongtest.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private BaseService baseService;
    private BaseRepository baseRepository;
    private UserRepository userRepository;

    @Autowired
    public UserService(BaseRepository baseRepository,
                       UserRepository userRepository,
                       BaseService baseService){
        this.baseRepository = baseRepository;
        this.userRepository = userRepository;
        this.baseService = baseService;
    }


    public UserModel login(String name, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{

        String resultToken = getToken(name, password);

        UserModel userModel = userRepository.findByName(name);

        HttpSession session = request.getSession();

        if(userModel != null) {
                if (BCrypt.checkpw(resultToken, userModel.getPassword())){

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(userModel.getAuthority()));
                    userModel.setAuthorities(authorities);

                    session.setAttribute("login", userModel);

                    return userModel;
                }

        }

        return null;
    }


    public UserModel insertUser(UserModel userModel) throws Exception{
        Optional<UserModel> userModelOptional = userRepository.findAllByName(userModel.getUsername());
        if (!userModelOptional.isPresent()) {
            try {
                String resultToken = getToken(userModel.getUsername(), userModel.getPassword());
                resultToken = getHashed(resultToken);
                logger.info(" Hashed Password : ", resultToken);

                userModel.setPassword(resultToken);
                userModel.setAuthority("ROLE_USER");
                userRepository.save(userModel);


            } catch (Exception e) {
                logger.info("Exception ===>   ", e);
            }
        }
        return userModel;
    }

    public UserModel updateUser(UserModel userModel) throws Exception {
        UserModel user = userRepository.findByName(userModel.getUsername());

        String resultToken = getToken(user.getUsername(), user.getPassword());
        if (BCrypt.checkpw(resultToken, userModel.getPassword())) {
            String requestToken = getToken(userModel.getUsername(), userModel.getPassword());
            requestToken = getHashed(requestToken);

            user.setPassword(requestToken);

        }
        user.setEmail(userModel.getEmail());
        userRepository.save(user);
        return user;
    }

    public String getToken(String id, String password) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> object = new HashMap<String, String>();
        object.put("typ", "JWT");
        object.put("alg", "HS256");
        String bytes = mapper.writeValueAsString(object);
        String headerResult = Base64.getUrlEncoder().encodeToString(bytes.getBytes());
        headerResult = headerResult.replaceAll("=", "");

        Map<String, String> object1 = new HashMap<String, String>();
        object1.put("iss", "mapyhwasong.com");
        object1.put("exp", "1485270000000");
        object1.put("https://github.com/songyunhwa/springBootProject_back", "true");
        object1.put("userId", id);
        object1.put("password", password);
        String bytes1 = mapper.writeValueAsString(object1);
        String bodyResult = Base64.getUrlEncoder().encodeToString(bytes1.getBytes());
        bodyResult = bodyResult.replaceAll("=", "");

        return headerResult + "." + bodyResult;
    }
    public String getHashed(String password){
        String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return passwordHashed;
    }

}
