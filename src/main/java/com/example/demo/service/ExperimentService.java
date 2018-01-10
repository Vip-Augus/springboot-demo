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
     *
     * @param ids 课程IDs
     * @return 实验课程列表
     */
    List<Experiment> getByIds(List<Integer> ids);

    /**
     * 某间实验室最近上的实验课
     *
     * @param cid           实验室地点id
     * @param day           周几
     * @param currentPeriod 当前时间段(格式:yyyy-MM-dd)
     * @return
     */
    List<Experiment> getUsingStatementByCID(Integer cid, Integer day, String currentPeriod);
}
