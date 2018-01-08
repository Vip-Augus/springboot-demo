package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@RestController
@RequestMapping(value = "/course")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getCourseList() {
        ListResult<Course> listResult = new ListResult<>();
        List<Course> courses = Lists.newArrayList();
        try {
            courses = courseService.getList();
            listResult.returnSuccess(courses);
        }catch (Exception e) {
            LOGGER.error("查询课程列表失败", e);
            listResult.returnError("查询课程列表失败");
        }
        return (JSON) JSON.toJSON(listResult);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public JSON getCourseDetail(@RequestParam("id") Integer id) {
        SingleResult<Course> result = new SingleResult<>();

        try {
            Course course = courseService.getById(id);
            result.returnSuccess(course);
        }catch (Exception e) {
            LOGGER.error("查询课程失败", e);
            result.returnError("查询课程失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateCourseDetail(@RequestBody Course course) {
        SingleResult<Course> result = new SingleResult<>();
        try {
            courseService.update(course);
            result.returnSuccess(course);
        }catch (Exception e) {
            LOGGER.error("修改课程失败", e);
            result.returnError("修改课程失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addCourseDetail(@RequestBody Course course) {
        SingleResult<Course> result = new SingleResult<>();
        try {
            courseService.add(course);
            result.returnSuccess(course);
        }catch (Exception e) {
            LOGGER.error("添加课程失败", e);
            result.returnError("添加课程失败");
        }
        return (JSON) JSON.toJSON(result);
    }
}
