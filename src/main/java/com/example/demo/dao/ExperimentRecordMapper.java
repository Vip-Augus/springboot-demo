package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.ExperimentRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Mapper
public interface ExperimentRecordMapper extends BaseMapperTemplate<ExperimentRecord> {

    /**
     * 根据实验课ID查询出每一次上课内容
     * @param epId      课程ID
     * @return          上课内容列表
     */
    List<ExperimentRecord> selectByEPId(@Param("epId") Integer epId);
}
