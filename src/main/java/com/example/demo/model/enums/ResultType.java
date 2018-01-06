package com.example.demo.model.enums;

import lombok.Data;

/**
 * Created by GJW on 2018/1/6.
 */

public enum ResultType {
    SUCCESS(200, "OK"),
    NO_AUTHORIZED(401,"没有权限访问这个页面！");

    private Integer code;
    private String decription;

    ResultType(Integer code, String decription) {
        this.code = code;
        this.decription = decription;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
}
