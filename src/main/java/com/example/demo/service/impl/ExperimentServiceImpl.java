package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentMapper;
import com.example.demo.model.Experiment;
import com.example.demo.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentServiceImpl implements ExperimentService{

    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public Experiment getById(Integer id) {
        return experimentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Experiment record) {
        return experimentMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Experiment add(Experiment record) {
        experimentMapper.insert(record);
        return record;
    }

    @Override
    public List<Experiment> getByIds(List<Integer> ids) {
        return experimentMapper.selectByIds(ids);
    }
}
