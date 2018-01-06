package com.example.demo.model.dto;

import java.io.Serializable;

/**
 * 实验室信息查询条件
 * Author by JingQ on 2018/1/6
 */
public class ClassroomQueryParam implements Serializable{

    private static final long serialVersionUID = -1001412393953094497L;

    private Integer buildingNumber;

    private String classroom;

    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassroomQueryParam{" +
                "buildingNumber=" + buildingNumber +
                ", classroom='" + classroom + '\'' +
                ", status=" + status +
                '}';
    }
}
