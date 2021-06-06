package com.example.yhwasongtest.user.service;

import com.example.yhwasongtest.user.dto.UserModelDto;
import com.example.yhwasongtest.user.model.Authority;
import com.example.yhwasongtest.user.model.CustomUserDetails;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.AuthorityRepository;
import com.example.yhwasongtest.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + "is not found.");
        }

        CustomUserDetails quickGuideUser = new CustomUserDetails();
        quickGuideUser.setUsername(user.getUsername());
        quickGuideUser.setPassword(user.getPassword());
        quickGuideUser.setAuthorities(getAuthorities(username));
        quickGuideUser.setEnabled(true);
        quickGuideUser.setAccountNonExpired(true);
        quickGuideUser.setAccountNonLocked(true);
        quickGuideUser.setCredentialsNonExpired(true);

        return quickGuideUser;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<Authority> authList = authorityRepository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }


    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(UserModelDto infoDto) throws Exception{
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //infoDto.setPassword(encoder.encode(infoDto.getPassword()));
        try {
            UserModel userModel = insertUser(infoDto);

            userRepository.save(userModel);
            return userModel.getId();
        }catch (Exception e) {
            throw new Exception();
        }
    }

    public UserModel insertUser(UserModelDto userModelDto) throws Exception{
        Optional<UserModel> userModelOptional = userRepository.findAllByUsername(userModelDto.getEmail());

        if (!userModelOptional.isPresent()) {
            try {

                String resultToken = getToken(userModelDto.getEmail(), userModelDto.getPassword());
                resultToken = getHashed(resultToken);

                logger.info(" Hashed Password : ", resultToken);
                UserModel userModel = new UserModel(userModelDto.getEmail(), resultToken, "ROLE_USER");
                return userModel;
            } catch (Exception e) {
                logger.info("Exception ===>   ", e);
            }
            ;
        }
        UserModel userModel = userRepository.findByUsername(userModelDto.getEmail());
        return userModel;
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

    public UserModel login(String name, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{

        String resultToken = getToken(name, password);

        UserModel userModel = userRepository.findByUsername(name);

        HttpSession session = request.getSession();

        if(userModel != null) {
            if (BCrypt.checkpw(resultToken, userModel.getPassword())){
                session.setAttribute("login", userModel);

                return userModel;
            }

        }

        return null;
    }

}