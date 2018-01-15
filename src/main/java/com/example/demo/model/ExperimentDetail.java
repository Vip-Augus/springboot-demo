package com.example.demo.model;

import java.io.Serializable;

/**
 * 实验课详情--提交记录
 * Author JingQ on 2017/12/24.
 */
public class ExperimentDetail implements Serializable{

    private static final long serialVersionUID = 6825913423924664739L;

    private Integer id;

    private String epFileUrl;

    private String epFileName;

    private String uploadDate;

    private String createDate;

    private String modifyDate;

    private Integer epId;

    private String score;

    private String comment;

    private String uploadName;

    private Integer epRecordId;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEpFileUrl() {
        return epFileUrl;
    }

    public void setEpFileUrl(String epFileUrl) {
        this.epFileUrl = epFileUrl == null ? null : epFileUrl.trim();
    }

    public String getEpFileName() {
        return epFileName;
    }

    public void setEpFileName(String epFileName) {
        this.epFileName = epFileName == null ? null : epFileName.trim();
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate == null ? null : uploadDate.trim();
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

    public Integer getEpId() {
        return epId;
    }

    public void setEpId(Integer epId) {
        this.epId = epId;
    }

    public Integer getEpRecordId() {
        return epRecordId;
    }

    public void setEpRecordId(Integer epRecordId) {
        this.epRecordId = epRecordId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    @Override
    public String toString() {
        return "ExperimentDetail{" +
                "id=" + id +
                ", epFileUrl='" + epFileUrl + '\'' +
                ", epFileName='" + epFileName + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", epId=" + epId +
                ", epRecordId=" + epRecordId +
                ", userId=" + userId +
                '}';
    }
}