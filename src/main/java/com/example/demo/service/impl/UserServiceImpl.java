package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5Util;
import com.example.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        //接收到用户自己设定的密码后，插入数据中，进行加密
        //加密算法password = md5(md5(password) + salt))
        String salt = UUID.randomUUID().toString();
        String password = MD5Util.genMD5(MD5Util.genMD5(record.getPassword()) + salt);
        record.setSalt(salt);
        record.setPassword(password);
        return userMapper.insert(record);
    }

    @Override
    public boolean checkPassword(User userParam, User user) {
        if (Objects.equals(user, null) || Objects.equals(userParam, null)) {
            return false;
        }
        String passwordCheck = MD5Util.genMD5(MD5Util.genMD5(userParam.getPassword()) + user.getSalt());
        return StringUtil.isEquals(passwordCheck, user.getPassword());
    }

    @Override
    public User getByIdNumber(String idNumber, Integer type) {
        return userMapper.selectByIdNumber(idNumber, type);
    }
}
