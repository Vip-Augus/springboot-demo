package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Course;
import com.example.demo.model.CourseReview;
import com.example.demo.model.enums.ReviewType;
import com.example.demo.service.CourseReviewService;
import com.example.demo.service.CourseService;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.Result;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@RestController
@RequestMapping(value = "/course/review")
public class CourseReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseReviewController.class);

    @Autowired
    CourseService courseService;

    @Autowired
    CourseReviewService courseReviewService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getCourseReviewList() {
        ListResult<CourseReview> listResult = new ListResult<>();
        List<CourseReview> reviews = Lists.newArrayList();
        try {
            reviews = courseReviewService.getListByState(ReviewType.UNREVIEW.getCode());
            listResult.returnSuccess(reviews);
        }catch (Exception e) {
            LOGGER.error("查询待审核课程列表失败", e);
            listResult.returnError("查询待审核课程列表失败");
        }
        return (JSON) JSON.toJSON(listResult);
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateCourseReviewState(@RequestParam("id") Integer id, @RequestParam("state") Integer state) {
        SingleResult<Course> result = new SingleResult<>();
        try {
            if(state == ReviewType.ACCEPT.getCode() || state == ReviewType.REFUSE.getCode()) {
                courseReviewService.updateState(id, state);
                if(state == ReviewType.ACCEPT.getCode()) {
                    Course course = getCourseFromReview(id);
                    courseService.add(course);
                    result.returnSuccess(course);
                }
            } else {
                result.returnError("参数非法");
            }
        }catch (Exception e) {
            LOGGER.error("更新审核课程状态失败", e);
            result.returnError("更新审核课程状态失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public JSON addCourseReview(@RequestBody CourseReview courseReview) {
        SingleResult<CourseReview> result = new SingleResult<>();
        try {
            CourseReview review = courseReviewService.add(courseReview);
            result.returnSuccess(review);
        }catch (Exception e) {
            LOGGER.error("申请创建课程失败", e);
            result.returnError("申请创建课程失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public JSON getCourseReviewDetail(@RequestParam("id") Integer id) {
        SingleResult<CourseReview> result = new SingleResult<>();

        try {
            CourseReview review = courseReviewService.getById(id);
            result.returnSuccess(review);
        }catch (Exception e) {
            LOGGER.error("查询待审核课程失败", e);
            result.returnError("查询待审核课程失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    private Course getCourseFromReview(int id) {
        CourseReview review = courseReviewService.getById(id);
        Course course = new Course();
        course.setName(review.getName());
        course.setBrief(review.getBrief());
        course.setCreateDate(new Date());
        course.setCreateId(review.getCreateId());
        course.setModifyDate(new Date());
        course.setPeriod(review.getPeriod());
        course.setTest(review.getTest());
        return course;
    }
}
