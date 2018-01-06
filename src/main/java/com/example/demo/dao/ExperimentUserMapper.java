package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.ExperimentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface ExperimentUserMapper extends BaseMapperTemplate<ExperimentUser> {

    /**
     * 根据用户ID查询选修的课
     * @param userId    用户id
     * @return      课程和用户的关系
     */
    List<ExperimentUser> selectByUserId(@Param("userId") Integer userId);

    /**
     * 批量插入
     * @param epId      实验课ID
     * @param userIds   用户ID列表
     * @return          插入的数量
     */
    int batchInsert(@Param("epId") Integer epId, @Param("userIds") List<Integer> userIds);

    List<Integer> selectUserIdsByEpId(@Param("epId") Integer epId);
}
