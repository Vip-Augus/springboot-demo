package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.College;
import com.example.demo.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 学院信息
 * Author by JingQ on 2018/1/1
 */
@RestController
@RequestMapping(value = "/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeServiceImpl;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addCollege(@RequestBody College college, HttpServletRequest request) {
        collegeServiceImpl.insert(college);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateCollege(@RequestBody College college, HttpServletRequest request) {
        collegeServiceImpl.update(college);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public void delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        collegeServiceImpl.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSONObject get(@RequestParam("id") Integer id, HttpServletRequest request) {
        College college = collegeServiceImpl.getById(id);
        return (JSONObject) JSON.toJSON(college);
    }
}
