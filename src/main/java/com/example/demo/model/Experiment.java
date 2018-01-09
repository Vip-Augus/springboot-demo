package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 实验课信息
 * Author JingQ on 2017/12/24.
 */
@Data
public class Experiment implements Serializable{

    private static final long serialVersionUID = -4725272480564053483L;

    private Integer id;

    private String name;

    private String briefIntroduction;

    private Integer classroomId;

    private String beginPeriod;

    private String endPeriod;

    /**
     * 周几
     */
    private Integer day;

    /**
     * 第几节课开始
     */
    private Integer classBegin;

    /**
     * 第几节课下课
     */
    private Integer classEnd;

    private Integer courseId;

    private String tIds;

    private String joinEndTime;

    private String createDate;

    private String modifyDate;

}