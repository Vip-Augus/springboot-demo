package com.example.demo.Interceptor;

import com.example.demo.model.User;
import com.example.demo.model.enums.UserType;
import com.example.demo.util.AuthUtil;
import com.example.demo.util.SessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
public class TeacherInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = SessionUtil.getUser(httpServletRequest.getSession());
        if(user.getType().equals(UserType.TEACHER.getCode()) || user.getType().equals(UserType.MANAGE.getCode())) {
            return true;
        }
        AuthUtil.NoAuthorityResponseOut(httpServletResponse, "老师");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
