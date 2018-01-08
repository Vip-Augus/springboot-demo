package com.example.demo.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.result.SingleResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
public class SessionInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String URI = httpServletRequest.getRequestURI();
        if(URI.equals("/user/login")) {
            return true;
        }
        User user =  (User) httpServletRequest.getSession().getAttribute("user_info");
        if(user == null) {
            SingleResult<UserDTO> result = new SingleResult<>();
            result.returnError(CodeConstants.LOGIN_AGAIN);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().append(JSON.toJSON(result).toString());
            httpServletResponse.getWriter().close();
            return false;//取消当前请求
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
