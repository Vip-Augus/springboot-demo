package com.example.demo.model;

import java.io.Serializable;

/**
 * 实验课信息
 * Author JingQ on 2017/12/24.
 */
public class Experiment implements Serializable{

    private static final long serialVersionUID = -4725272480564053483L;

    private Integer id;

    private String name;

    private String briefIntroduction;

    private Integer classroomId;

    private String beginPeriod;

    private String endPeriod;

    private String tIds;

    private String joinEndTime;

    private String createDate;

    private String modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction == null ? null : briefIntroduction.trim();
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
        this.beginPeriod = beginPeriod == null ? null : beginPeriod.trim();
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod == null ? null : endPeriod.trim();
    }

    public String gettIds() {
        return tIds;
    }

    public void settIds(String tIds) {
        this.tIds = tIds == null ? null : tIds.trim();
    }

    public String getJoinEndTime() {
        return joinEndTime;
    }

    public void setJoinEndTime(String joinEndTime) {
        this.joinEndTime = joinEndTime == null ? null : joinEndTime.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate == null ? null : modifyDate.trim();
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", briefIntroduction='" + briefIntroduction + '\'' +
                ", classroomId='" + classroomId + '\'' +
                ", beginPeriod='" + beginPeriod + '\'' +
                ", endPeriod='" + endPeriod + '\'' +
                ", tIds='" + tIds + '\'' +
                ", joinEndTime='" + joinEndTime + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }
}