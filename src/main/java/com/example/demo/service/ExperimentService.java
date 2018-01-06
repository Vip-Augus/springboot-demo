package com.example.demo.service;

import com.example.demo.model.Experiment;
import com.example.demo.base.BaseServiceTemplate;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
public interface ExperimentService extends BaseServiceTemplate<Experiment> {

    /**
     * 批量查询
     * @param ids   课程IDs
     * @return      实验课程列表
     */
    List<Experiment> getByIds(List<Integer> ids);
}
