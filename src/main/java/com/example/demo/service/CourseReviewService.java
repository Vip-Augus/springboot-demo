package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.CourseReview;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
public interface CourseReviewService extends BaseServiceTemplate<CourseReview> {
    List<CourseReview> getListByState(int state);

    void updateState(int id, int state);
}
