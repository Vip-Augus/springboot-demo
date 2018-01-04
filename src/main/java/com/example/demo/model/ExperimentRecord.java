package com.example.demo.model;

import java.io.Serializable;

/**
 * 实验课记录--每一节课信息
 * Author JingQ on 2017/12/24.
 */
public class ExperimentRecord implements Serializable{

    private static final long serialVersionUID = 5917177207935651806L;

    private Integer id;

    private String epName;

    private String epFileUrl;

    private Integer epId;

    private String date;

    private String uploadEndTime;

    private Integer classBegin;

    private Integer classEnd;

    private Integer classroomId;

    private String createDate;

    private String modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName == null ? null : epName.trim();
    }

    public String getEpFileUrl() {
        return epFileUrl;
    }

    public void setEpFileUrl(String epFileUrl) {
        this.epFileUrl = epFileUrl == null ? null : epFileUrl.trim();
    }

    public Integer getEpId() {
        return epId;
    }

    public void setEpId(Integer epId) {
        this.epId = epId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getUploadEndTime() {
        return uploadEndTime;
    }

    public void setUploadEndTime(String uploadEndTime) {
        this.uploadEndTime = uploadEndTime == null ? null : uploadEndTime.trim();
    }

    public Integer getClassBegin() {
        return classBegin;
    }

    public void setClassBegin(Integer classBegin) {
        this.classBegin = classBegin;
    }

    public Integer getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(Integer classEnd) {
        this.classEnd = classEnd;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
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
        return "ExperimentRecord{" +
                "id=" + id +
                ", epName='" + epName + '\'' +
                ", epFileUrl='" + epFileUrl + '\'' +
                ", epId=" + epId +
                ", date='" + date + '\'' +
                ", uploadEndTime='" + uploadEndTime + '\'' +
                ", classBegin=" + classBegin +
                ", classEnd=" + classEnd +
                ", classroomId='" + classroomId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }
}