package com.example.demo.service.impl;

import com.example.demo.dao.CourseReviewMapper;
import com.example.demo.model.CourseReview;
import com.example.demo.service.CourseReviewService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@Service
public class CourseReviewServiceImpl implements CourseReviewService {

    @Autowired
    CourseReviewMapper courseReviewMapper;
    @Override
    public CourseReview getById(Integer id) {
        return courseReviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(CourseReview record) {
        return courseReviewMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Integer id) {
        return courseReviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CourseReview add(CourseReview record) {
        courseReviewMapper.insert(record);
        return record;
    }

    @Override
    public List<CourseReview> getListByState(int state) {
        List<CourseReview> reviews = Lists.newArrayList();
        reviews = courseReviewMapper.getListByState(state);
        if (reviews == null) {
            return Collections.emptyList();
        }
        return reviews;
    }


    @Override
    public void updateState(int id, int state) {
        courseReviewMapper.updateReviewState(id, state);
    }
}
