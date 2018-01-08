package com.example.demo.util;

/**
 * @author guojiawei
 * @date 2018/1/8
 */
public class AuthUtil {
    public static boolean checkAuthority(int auth, int type) {
        return ((auth >> type) & 1) == 1;
    }

    public static void main(String[] args) {
        System.out.println(checkAuthority(1, 1));
        System.out.println(checkAuthority(1, 0));
        System.out.println(checkAuthority(4, 3));
        System.out.println(checkAuthority(5, 2));
    }
}
