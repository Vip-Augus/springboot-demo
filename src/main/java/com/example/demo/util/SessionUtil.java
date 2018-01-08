package com.example.demo.util;

import com.example.demo.model.User;

import javax.servlet.http.HttpSession;

/**
 * 会话工具类
 * Author by JingQ on 2018/1/1
 */
public class SessionUtil {

    /**
     * 从session中获取用户数据
     * @param session       用户请求
     * @return              用户数据
     * @throws Exception    用户没有登录
     */
    public static User getUser(HttpSession session) throws Exception {
        User user = (User) session.getAttribute(CodeConstants.USER_INFO_CONSTANT);
        if (user == null) {
            throw new Exception(CodeConstants.NULL_USER_INFO);
        }
        return user;
    }

}
