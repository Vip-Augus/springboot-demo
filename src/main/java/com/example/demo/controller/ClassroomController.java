package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Classroom;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.model.dto.ClassroomQueryParam;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.ExperimentRecordService;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 实验室地点控制器
 * Author by JingQ on 2018/1/4
 */
@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomServiceImpl;

    @Autowired
    private ExperimentRecordService experimentRecordServiceImpl;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addClassroom(@RequestBody Classroom classroom, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        result.returnSuccess(classroomServiceImpl.add(classroom));
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateClassroom(@RequestBody Classroom classroom, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        classroomServiceImpl.update(classroom);
        result.returnSuccess(classroom);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getClassroom(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Classroom> result = new SingleResult<>();
        Classroom classroom = classroomServiceImpl.getById(id);
        result.returnSuccess(classroom);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteClassroom(@RequestParam("id") Integer id, HttpServletRequest request) {
        classroomServiceImpl.deleteById(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public JSON query(@RequestBody ClassroomQueryParam queryParam, HttpServletRequest request) {
        ListResult<Classroom> result = new ListResult<>();
        result.returnSuccess(classroomServiceImpl.getList(queryParam));
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryDetail(@RequestParam("id") Integer id, HttpServletRequest request) {
        ListResult<ExperimentRecord> result = new ListResult<>();
        result.returnSuccess(experimentRecordServiceImpl.getListByClassroomId(id));
        return (JSON) JSON.toJSON(result);
    }

}
