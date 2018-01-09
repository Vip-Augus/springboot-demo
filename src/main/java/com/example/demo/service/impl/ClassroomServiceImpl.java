package com.example.demo.service.impl;

import com.example.demo.dao.ClassroomMapper;
import com.example.demo.model.Classroom;
import com.example.demo.model.Experiment;
import com.example.demo.model.dto.ClassroomQueryParam;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author by JingQ on 2018/1/4
 */
@Service
public class ClassroomServiceImpl implements ClassroomService{

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private ExperimentService experimentServiceImpl;

    @Override
    public Classroom getById(Integer id) {
        return classroomMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Classroom record) {
        return classroomMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return classroomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Classroom add(Classroom record) {
        classroomMapper.insert(record);
        return record;
    }

    @Override
    public List<Classroom> getList(ClassroomQueryParam queryParam) {
        return classroomMapper.query(queryParam);
    }

    @Override
    public List<Experiment> getUsingStatement(Integer cid, String currentTime) {
        return experimentServiceImpl.getUsingStatementByCID(cid, currentTime);
    }
}
