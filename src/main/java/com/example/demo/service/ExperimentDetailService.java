package com.example.demo.service;

import com.example.demo.model.ExperimentDetail;
import com.example.demo.base.BaseServiceTemplate;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
public interface ExperimentDetailService extends BaseServiceTemplate<ExperimentDetail>{

    /**
     * 根据recordId和用户ID查询提交的作业(老师是看到所有学生, 学生只能看到自己的提交记录)
     * @param epRecordId    实验记录ID
     * @param userId        用户ID
     * @return              作业列表
     */
    List<ExperimentDetail> getDetailsByEPRecordId(Integer epRecordId, Integer userId);
}
