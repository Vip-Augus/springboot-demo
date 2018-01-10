package com.example.demo.model.enums;

import lombok.Getter;

/**
 * 实验室状态
 * @author by JingQ on 2018/1/9
 */
public enum ClassroomStatus {

    /**
     * 数据库落库字段只有0和1(是否正常使用)
     * 实验室占用状态会在展示中用到
     */
    STOP(0, "停止使用"),
    NORMAL(1, "正常使用"),
    TAKE_UP(2, "上课中"),
    FREE(3, "空闲");

    private int code;

    private String description;

    ClassroomStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ClassroomStatus fromCode(int code) {
        for (ClassroomStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
