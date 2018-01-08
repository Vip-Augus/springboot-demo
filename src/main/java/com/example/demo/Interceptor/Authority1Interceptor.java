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
public class Authority1Interceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //System.out.println("URL:" + httpServletRequest.getRequestURL());
        //System.out.println("URI:" + httpServletRequest.getRequestURI());
        String URI = httpServletRequest.getRequestURI();
        //System.out.println("URI: " + URI);
        if(URI.equals("/user/login")) {
            return true;
        }
        if(URI.equals("/")) {
            User user =  (User) httpServletRequest.getSession().getAttribute("user_info");
            String auth_str = user.getAuthority();
            Integer auth_Int = Integer.valueOf(auth_str,16);
            Integer auth = 1, auth_num = 1;
            auth <<= auth_num;
            if((auth_Int & auth) == 1)return true;
            else {
                SingleResult<UserDTO> result = new SingleResult<>();
                result.returnError(CodeConstants.NO_AUTHORITY);
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().append(JSON.toJSON(result).toString());
                httpServletResponse.getWriter().close();
                return false;
            }
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
