package com.example.demo.model.enums;

import java.util.Objects;

/**
 * 用户属性
 * Author JingQ on 2017/12/26.
 */
public enum UserType {

    MANAGE(0, "管理员"),
    TEACHER(1, "老师"),
    STUDENT(2, "学生");

    private Integer code;

    private String description;

    UserType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserType fromCode(Integer code) {
        for (UserType type : UserType.values()) {
            if (Objects.equals(type.code, code)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }

}
