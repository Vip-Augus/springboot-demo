package com.example.demo.model.dto;

import java.io.Serializable;

/**
 * Author by JingQ on 2018/1/1
 */
public class UserDTO implements Serializable{

    private static final long serialVersionUID = 3284926612682375926L;

    private Integer id;

    private String idNumber;

    private String name;

    private String phone;

    private String birthday;

    private String address;

    private Integer collegeId;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", collegeId=" + collegeId +
                ", type=" + type +
                '}';
    }
}
