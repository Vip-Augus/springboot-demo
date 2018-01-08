package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Course;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
public interface CourseService extends BaseServiceTemplate<Course> {
    List<Course> getList();
}
