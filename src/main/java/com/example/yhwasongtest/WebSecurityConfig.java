package com.example.yhwasongtest;

import com.example.yhwasongtest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity // 1
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // 2

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception { // 5

            http.csrf().disable();		//로그인 창
            http.authorizeRequests()
                    .antMatchers("/api/v1/login", "/api/v1/user").anonymous()
                    .antMatchers("/api/v1/place/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN") // '/admin/*' 요청은 ADMIN 권한을 가진 사용자만 접근 가능
                    .anyRequest().authenticated(); // 그 외 모든 요청은 인증된 사용자만 접근 가능



    }

    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
