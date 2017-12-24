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
     * @param ids   实验课ID列表
     * @return      实验课列表
     */
    List<Experiment> selectByIds(@Param("ids") List<Integer> ids);
}
