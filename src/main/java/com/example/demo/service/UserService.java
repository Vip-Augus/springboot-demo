package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.base.BaseServiceTemplate;

/**
 * Author JingQ on 2017/12/24.
 */
public interface UserService extends BaseServiceTemplate<User> {

    /**
     * 校验密码
     * @param userParam     用户参数（用户填的）
     * @param user          用户数据（数据库查询的）
     * @return              校验结果
     */
    boolean checkPassword(User userParam, User user);

    /**
     * 用户查询
     * @param idNumber      工号或者学号
     * @param type          用户类型
     * @return
     */
    User getByIdNumber(String idNumber, Integer type);
}
