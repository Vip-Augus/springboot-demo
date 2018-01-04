package com.example.demo.service.impl;

import com.example.demo.dao.CollegeMapper;
import com.example.demo.model.College;
import com.example.demo.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class CollegeServiceImpl implements CollegeService{

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public College getById(Integer id) {
        return collegeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(College record) {
        return collegeMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return collegeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public College add(College record) {
        collegeMapper.insert(record);
        return record;
    }
}
