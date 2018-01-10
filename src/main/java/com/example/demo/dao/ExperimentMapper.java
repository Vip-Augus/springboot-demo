package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.Experiment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface ExperimentMapper extends BaseMapperTemplate<Experiment> {

    /**
     * 批量查询
     *
     * @param ids 实验课ID列表
     * @return 实验课列表
     */
    List<Experiment> selectByIds(@Param("ids") List<Integer> ids);

    /**
     * 查询实验室使用情况--正在上的实验课
     *
     * @param classroomId 实验室ID
     * @param day         周几
     * @param currentTime 当前时间
     * @return
     */
    List<Experiment> selectInUseByClassroomId(@Param("classroomId") Integer classroomId, @Param("day") Integer day, @Param("currentTime") String currentTime);
}
