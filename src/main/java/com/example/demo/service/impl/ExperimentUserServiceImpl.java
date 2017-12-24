package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentUserMapper;
import com.example.demo.model.ExperimentUser;
import com.example.demo.service.ExperimentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentUserServiceImpl implements ExperimentUserService{

    @Autowired
    private ExperimentUserMapper experimentUserMapper;

    @Override
    public ExperimentUser getById(Integer id) {
        return experimentUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ExperimentUser record) {
        return experimentUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ExperimentUser record) {
        return experimentUserMapper.insert(record);
    }
}
