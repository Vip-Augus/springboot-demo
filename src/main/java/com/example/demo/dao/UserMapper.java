package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface UserMapper extends BaseMapperTemplate<User> {
}
