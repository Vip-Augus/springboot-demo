package com.example.demo.model.dto;

import com.example.demo.base.BaseDTO;
import com.example.demo.model.User;

import java.util.List;

/**
 * 实验课信息
 * Author by JingQ on 2018/1/2
 */
public class ExperimentDTO extends BaseDTO {

    private String name;

    private String briefIntroduction;

    private Integer classroomId;

    private String beginPeriod;

    private String endPeriod;

    private String tIds;

    private String joinEndTime;

    private List<User> teachers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(String beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String gettIds() {
        return tIds;
    }

    public void settIds(String tIds) {
        this.tIds = tIds;
    }

    public String getJoinEndTime() {
        return joinEndTime;
    }

    public void setJoinEndTime(String joinEndTime) {
        this.joinEndTime = joinEndTime;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }
}
