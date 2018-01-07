package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface UserMapper extends BaseMapperTemplate<User> {

    /**
     * 查询用户
     * @param idNumber  学号或者工号
     * @param type      用户类型
     * @return          用户数据
     */
    User selectByIdNumber(@Param("idNumber") String idNumber, @Param("type") Integer type);

    /**
     * 批量查询用户(type区分用户类型)
     * @param idNumbers 学号或工号列表
     * @param type      用户类型
     * @return          用户数据列表
     */
    List<User> selectByIdNumbers(@Param("idNumbers") List<String> idNumbers, @Param("type") Integer type);

    /**
     * 根据type查找（学生，老师）
     * @param type
     * @return
     */
    List<User> selectByType( @Param("type") Integer type);

    /**
     * 根据idNumber删除User
     * @param idNumber
     */
    void deleteByIdNumber(@Param("idNumber") String idNumber);
}
