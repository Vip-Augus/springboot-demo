package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.util.result.SingleResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guojiawei
 * @date 2018/1/8
 */
public class AuthUtil {
    public static boolean checkAuthority(int auth, int type) {
        return ((auth >> type) & 1) == 1;
    }

    public  static void NoAuthorityResponseOut(HttpServletResponse httpServletResponse, String describe) throws IOException {
        SingleResult<UserDTO> result = new SingleResult<>();
        result.returnError(String.format("没有%s权限", describe));
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().append(JSON.toJSON(result).toString());
        httpServletResponse.getWriter().close();
    }

    public static void main(String[] args) {
        System.out.println(checkAuthority(1, 1));
        System.out.println(checkAuthority(1, 0));
        System.out.println(checkAuthority(4, 3));
        System.out.println(checkAuthority(5, 2));
    }
}
