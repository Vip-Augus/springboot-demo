package com.example.demo.dao;

import com.example.demo.model.CourseReview;

public interface CourseReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseReview record);

    int insertSelective(CourseReview record);

    CourseReview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseReview record);

    int updateByPrimaryKey(CourseReview record);
}