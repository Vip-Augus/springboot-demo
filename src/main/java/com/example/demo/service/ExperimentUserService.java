package com.example.demo.service;

import com.example.demo.model.ExperimentUser;
import com.example.demo.base.BaseServiceTemplate;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
public interface ExperimentUserService extends BaseServiceTemplate<ExperimentUser> {

    /**
     * 根据用户id查询所选课程
     * @param userId    用户ID
     * @return          实验课程列表
     */
    List<Integer> getEPIDsByUserID(Integer userId);

    /**
     * 批量插入
     * @param epId      实验课程ID
     * @param userIds   用户ID列表
     * @return          批量插入的数量
     */
    int batchAdd(Integer epId, List<Integer> userIds);

    List<Integer> getUserIdsByEpId(Integer epId);
}
