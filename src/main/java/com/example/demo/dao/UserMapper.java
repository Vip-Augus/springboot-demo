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
     *
     * @param idNumber 学号或者工号
     * @param type     用户类型
     * @return 用户数据
     */
    User selectByIdNumber(@Param("idNumber") String idNumber, @Param("type") Integer type);

    /**
     * 批量查询用户(type区分用户类型)
     *
     * @param idNumbers 学号或工号列表
     * @param type      用户类型
     * @return 用户数据列表
     */
    List<User> selectByIdNumbers(@Param("idNumbers") List<String> idNumbers, @Param("type") Integer type);

    /**
     * 根据type查找（学生，老师）
     *
     * @param type
     * @return
     */
    List<User> selectByType(@Param("type") Integer type);

    /**
     * 查找老师(type = 0 or 1)
     *
     * @return
     */
    List<User> selectTeachers(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);

    /**
     * 根据idNumber删除User
     *
     * @param idNumber
     */
    boolean deleteByIdNumber(@Param("idNumber") String idNumber);

    /**
     * 修改User
     *
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 批量插入用户
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<User> list);

    /**
     * 更新老师权限
     *
     * @param userId    用户id
     * @param auth      权限
     * @return          结果
     */
    boolean updateAuth(@Param("userId") Integer userId, @Param("auth") String auth, @Param("type") Integer type);
}
