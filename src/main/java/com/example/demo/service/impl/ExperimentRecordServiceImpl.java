package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentRecordMapper;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.service.ExperimentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentRecordServiceImpl implements ExperimentRecordService {

    @Autowired
    private ExperimentRecordMapper experimentRecordMapper;

    @Override
    public ExperimentRecord getById(Integer id) {
        return experimentRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ExperimentRecord record) {
        return experimentRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ExperimentRecord add(ExperimentRecord record) {
        experimentRecordMapper.insert(record);
        return record;
    }

    @Override
    public List<ExperimentRecord> getListByEPId(Integer epId) {
        return experimentRecordMapper.selectByEPId(epId);
    }

    @Override
    public List<ExperimentRecord> getListByClassroomId(Integer classroomId) {
        return experimentRecordMapper.selectByClassroomId(classroomId);
    }
}
