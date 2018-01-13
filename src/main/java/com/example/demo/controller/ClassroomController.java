package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Classroom;
import com.example.demo.model.Experiment;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.model.dto.ClassroomQueryParam;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.ExperimentRecordService;
import com.example.demo.util.PeriodUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 实验室地点控制器
 * Author by JingQ on 2018/1/4
 */
@RestController
@RequestMapping("/web/classroom")
public class ClassroomController {

    private static final String TIME_TEMPLATE = "yyyy-MM-dd";

    private Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);

    @Autowired
    private ClassroomService classroomServiceImpl;

    @Autowired
    private ExperimentRecordService experimentRecordServiceImpl;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addClassroom(@RequestBody Classroom classroom, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        try {
            classroom = classroomServiceImpl.add(classroom);
            LOGGER.info("成功添加实验室: ", classroom);
        } catch (Exception e) {
            LOGGER.error("添加实验室失败: ", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(classroom);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateClassroom(@RequestBody Classroom classroom, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        try {
            classroomServiceImpl.update(classroom);
            LOGGER.info("实验室信息更新成功:", classroom);
        } catch (Exception e) {
            LOGGER.error("实验室信息更新失败:", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(classroom);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getClassroom(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        Classroom classroom;
        try {
            classroom = classroomServiceImpl.getById(id);
        } catch (Exception e) {
            LOGGER.error("查询实验室失败: ", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(classroom);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteClassroom(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            classroomServiceImpl.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("删除实验室失败: ", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(id);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public JSON query(@RequestBody ClassroomQueryParam queryParam, HttpServletRequest request) {
        ListResult<Classroom> result = new ListResult<>();
        try {
            result.returnSuccess(classroomServiceImpl.getList(queryParam));
        } catch (Exception e) {
            LOGGER.error("根据条件查询失败:", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryDetail(@RequestParam("id") Integer id, HttpServletRequest request) {
        ListResult<Experiment> result = new ListResult<>();
        try {
            SimpleDateFormat sdf = PeriodUtil.getSimpleDateFormat(TIME_TEMPLATE);
            String currentTime = sdf.format(System.currentTimeMillis());
            result.returnSuccess(classroomServiceImpl.getUsingStatement(id, currentTime));
        } catch (Exception e) {
            LOGGER.error("查询最近上的实验课失败:", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

}
