package com.example.demo.model.enums;

import com.example.demo.util.PeriodUtil;

/**
 * 上课时间对应枚举
 *
 * @author by JingQ on 2018/1/9
 */
public enum ClassTime {

    /**
     * 每节课对应的上课时间
     */
    ONE(1, "8:00", "8:50", "第一节课"),
    TWO(2, "8:50", "9:45", "第二节课"),
    THREE(3, "9:45", "10:45", "第三节课"),
    FOUR(4, "10:45", "11:35", "第四节课"),
    FIVE(5, "11:35", "12:25", "第五节课"),
    SIX(6, "13:30", "14:15", "第六节课"),
    SEVEN(7, "14:15", "15:05", "第七节课"),
    EIGHT(8, "15:05", "16:00", "第八节课"),
    NINE(9, "16:00", "16:50", "第九节课"),
    TEN(10, "18:30", "19:15", "第十节课"),
    ELEVEN(11, "19:15", "20:05", "第十一节课"),
    TWELVE(12, "20:05", "20:55", "第十二节课"),
    OTHER(13, "00:00", "01:00", "休息的课");


    private Integer code;
    private String classBeginTime;
    private String classEndTime;
    private String description;

    ClassTime(Integer code, String classBeginTime, String classEndTime, String description) {
        this.code = code;
        this.classBeginTime = classBeginTime;
        this.classEndTime = classEndTime;
        this.description = description;
    }

    public static ClassTime fromCode(Integer code) {
        for (ClassTime time : values()) {
            if (time.getCode().equals(code)) {
                return time;
            }
        }
        return OTHER;
    }

    /**
     * 根据当前时间判断在上那一节课
     *
     * @param currentTime 当前时间
     * @return 课时
     */
    public static ClassTime fromTime(String currentTime) {
        if (!PeriodUtil.checkClassTimeFormat(currentTime)) {
            return null;
        }
        for (ClassTime time : values()) {
            if (time.getClassBeginTime().compareTo(currentTime) <= 0
                    && time.getClassEndTime().compareTo(currentTime) >= 0) {
                return time;
            }
        }
        return OTHER;
    }

    public Integer getCode() {
        return code;
    }

    public String getClassBeginTime() {
        return classBeginTime;
    }

    public String getClassEndTime() {
        return classEndTime;
    }

    public String getDescription() {
        return description;
    }
}
