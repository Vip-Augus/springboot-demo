package com.example.demo.model.enums;

import lombok.Getter;

/**
 * @author guojiawei
 * @date 2018/1/8
 */
public enum AuthorityType {

    /**
     * 0对应authority字段右边第一位
     */
    AUTHORITY_PERMISSION(0, "权限分配"),

    COURSE_REVIEW(1, "课程审核"),

    CLASSROOM(2, "实验室"),

    SEND_NOTICE(3, "发布公告");





    @Getter
    private int authCode;
    @Getter
    private String describe;

    AuthorityType(int authCode, String describe) {
        this.authCode = authCode;
        this.describe = describe;
    }
}
