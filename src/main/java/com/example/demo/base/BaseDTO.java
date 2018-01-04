package com.example.demo.base;

import java.io.Serializable;

/**
 * Author by JingQ on 2018/1/2
 */
public class BaseDTO implements Serializable{

    private static final long serialVersionUID = -910695271951878036L;

    private Integer id;

    private String createDate;

    private String modifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
