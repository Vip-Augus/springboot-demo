package com.example.demo.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.result.SingleResult;
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
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
