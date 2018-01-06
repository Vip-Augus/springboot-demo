package com.example.demo.service;

import com.example.demo.model.ExperimentRecord;
import com.example.demo.base.BaseServiceTemplate;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
public interface ExperimentRecordService extends BaseServiceTemplate<ExperimentRecord> {

    /**
     * 查询
     * @param epId  实验课ID
     * @return      实验记录
     */
    List<ExperimentRecord> getListByEPId(Integer epId);

    /**
     * 查询
     * @param classroomId   实验室ID
     * @return              实验记录
     */
    List<ExperimentRecord> getListByClassroomId(Integer classroomId);

}
