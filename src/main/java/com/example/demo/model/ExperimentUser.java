package com.example.demo.model;

import java.io.Serializable;

/**
 * 实验课和用户的关系--谁选修了这门课
 * Author JingQ on 2017/12/24.
 */
public class ExperimentUser implements Serializable{

    private static final long serialVersionUID = -7173595158935962596L;

    private Integer id;

    private Integer epId;

    private Integer userId;

    private String createDate;

    private String modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEpId() {
        return epId;
    }

    public void setEpId(Integer epId) {
        this.epId = epId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return "ExperimentUser{" +
                "id=" + id +
                ", epId=" + epId +
                ", userId=" + userId +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }
}