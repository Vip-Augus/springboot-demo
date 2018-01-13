package com.example.demo.model;

import java.io.Serializable;

/**
 * 实验室地点
 * Author by JingQ on 2018/1/4
 */
public class Classroom implements Serializable{

    private static final long serialVersionUID = 1917725736837406977L;

    private Integer id;

    private Integer buildingNumber;

    private String classroom;

    private Integer capacity;

    /**
     *实验室负责人,cyq说要加的
     */
    private String principal;

    private Integer status;

    private String createDate;

    private String modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", buildingNumber=" + buildingNumber +
                ", classroom='" + classroom + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }
}
