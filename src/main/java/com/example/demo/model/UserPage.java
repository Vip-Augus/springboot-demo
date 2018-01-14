package com.example.demo.model;

import java.util.List;

/**
 * 用户分页
 *
 * @author by JingQ on 2018/1/14
 */
public class UserPage {

    private Integer totalCount;

    private List<User> list;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
