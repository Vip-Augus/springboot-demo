package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用户分页DTO
 *
 * @author by JingQ on 2018/1/14
 */
public class UserPageDTO implements Serializable{

    private static final long serialVersionUID = -7439387976732919912L;
    private Integer totalCount;

    private List<UserDTO> list;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserDTO> getList() {
        return list;
    }

    public void setList(List<UserDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserPageDTO{" +
                "totalCount=" + totalCount +
                ", list=" + list +
                '}';
    }
}
