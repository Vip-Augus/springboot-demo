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
 * Created by Administrator on 2018/1/5 0005.
 */
public class AuthorityInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //System.out.println("URL:" + httpServletRequest.getRequestURL());
        //System.out.println("URI:" + httpServletRequest.getRequestURI());
        String URI = httpServletRequest.getRequestURI();
        //System.out.println("URI: " + URI);
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
        String authority = user.getAuthority();
        Integer auth = Integer.valueOf(authority,16);
        switch (URI) {
            case "/user/updateUserAuth":
                if((auth & 1) == 1)return true;
                else {
                    SingleResult<UserDTO> result = new SingleResult<>();
                    result.returnError(CodeConstants.NO_AUTHORITY);
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.getWriter().append(JSON.toJSON(result).toString());
                    httpServletResponse.getWriter().close();
                }
            default:
                break;
        }
        //String idNumber = user.getIdNumber();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //User user =  (User) httpServletRequest.getSession().getAttribute("user_info");
        //String idNumber = user.getIdNumber();
        //System.out.println(idNumber);
        /*String authority = user.getAuthority();
        Integer in = Integer.valueOf(authority,16);
        String stb = Integer.toBinaryString(in);
        char c = stb.charAt(0);*/
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
