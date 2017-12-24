package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentDetailMapper;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.service.ExperimentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentDetailServiceImpl implements ExperimentDetailService {

    @Autowired
    private ExperimentDetailMapper experimentDetailMapper;

    @Override
    public ExperimentDetail getById(Integer id) {
        return experimentDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ExperimentDetail record) {
        return experimentDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ExperimentDetail record) {
        return experimentDetailMapper.insert(record);
    }
}
