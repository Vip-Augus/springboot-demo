package com.example.demo.model.enums;

import lombok.Data;

/**
 * Created by GJW on 2018/1/7.
 */

public enum ReviewType {
    UNREVIEW(0, "未审核"),
    ACCEPT(1, "通过"),
    REFUSE(2, "拒绝");

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Integer code;

    private String description;

    ReviewType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
