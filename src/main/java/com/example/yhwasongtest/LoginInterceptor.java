package com.example.yhwasongtest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN = "login";

    @Override
    public void postHandle (HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                             ModelAndView modelAndView) throws Exception {


        HttpSession session = request.getSession();

        Object userVO = session.getAttribute("login");


        if(userVO == null) {
            // 로그인 성공시 Session에 저장후, 초기 화면 이동
            Object dest = session.getAttribute("currentQuery");
            response.sendRedirect(dest != null ? (String)dest : "/main");
        } else {
            log.info("Session is not null " );
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();

        if(session.getAttribute(LOGIN) != null) {
            saveQuery(request);
            // 기존 HttpSession에 남아있는 정보가 있는 경우 이를 삭제
            //session.removeAttribute(LOGIN);
        } else {

            response.sendRedirect("/api/v1/login");
            return false;
        }

        return true;
    }

    public void saveQuery(HttpServletRequest request){
        // 페이지 저장
        String uri = request.getRequestURI();
        String query = request.getQueryString();

        if(query == null || query.equals("null")){
            query = "";
        } else {
            query = "?"+ query;
        }
        if(request.getMethod().equals("GET")){
            request.getSession().setAttribute("currentQuery", uri + query);
        }
    }
}
