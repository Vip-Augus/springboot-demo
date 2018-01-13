package com.example.demo.model.enums;

/**
 * Created by GJW on 2018/1/6.
 */

public enum ResultType {
    SUCCESS("200", "OK"),
    NO_AUTHORIZED("401","没有权限访问这个页面！");

    private String code;
    private String description;

    ResultType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
