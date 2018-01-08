package com.example.demo.dao;

import com.example.demo.model.CourseReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseReview record);

    int insertSelective(CourseReview record);

    CourseReview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseReview record);

    int updateByPrimaryKey(CourseReview record);

    List<CourseReview> getListByState(@Param("state") int state);

    void updateReviewState(@Param("id") int id, @Param("state") int state);
}