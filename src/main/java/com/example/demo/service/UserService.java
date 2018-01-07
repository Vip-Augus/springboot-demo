package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.base.BaseServiceTemplate;

import java.util.List;

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
     * @return              用户数据
     */
    User getByIdNumber(String idNumber, Integer type);

    /**
     * 批量查询
     * @param idNumbers     学号或者工号列表
     * @param type          用户类型
     * @return              用户列表
     */
    List<User> getByIdNumbers(List<String> idNumbers, Integer type);

    /**
     * 根据type查找（学生，老师）
     * @param type
     * @return
     */
    List<User> getByType(Integer type);

    /**
     * 根据idNumber删除User
     * @param idNumber
     */
    void deleteByIdNumber(String idNumber);
}
