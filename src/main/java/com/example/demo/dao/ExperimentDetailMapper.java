package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.ExperimentDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface ExperimentDetailMapper extends BaseMapperTemplate<ExperimentDetail> {

    /**
     * 根据每一次实验课查找到学生提交记录---老师用,查询全部
     * @param epRecordId    实验课记录ID
     * @return              学生提交记录
     */
    List<ExperimentDetail> selectByEPRecordId(@Param("epRecordId") Integer epRecordId);

    /**
     * 根据每一次实验课查找到学生提交记录---学生用,查出学生个人的提交记录
     * @param epRecordId    实验课记录ID
     * @return              学生提交记录
     */
    List<ExperimentDetail> selectByEPRecordAndUserId(@Param("epRecordId") Integer epRecordId, @Param("userId") Integer userId);
}
