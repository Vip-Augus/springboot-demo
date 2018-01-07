package com.example.demo.service.impl;

import com.example.demo.dao.CourseMapper;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Override
    public Course getById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Course record) {
        return courseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Integer id) {
        return courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Course add(Course record) {
        courseMapper.insert(record);
        return record;
    }

    @Override
    public List<Course> getList() {
        return courseMapper.getList();
    }
}
