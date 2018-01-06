package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentRecord;
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

/**
 * 实验课记录控制器
 * Author by JingQ on 2018/1/6
 */
@RestController
@RequestMapping("/epRecord")
public class ExperimentRecordController {

    private static final String BASE_DIR = "record/";
    @Autowired
    private ExperimentRecordService experimentRecordServiceImpl;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addRecord(@RequestBody ExperimentRecord record, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        result.returnSuccess(experimentRecordServiceImpl.add(record));
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateRecord(@RequestBody ExperimentRecord record, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        experimentRecordServiceImpl.update(record);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        ExperimentRecord record = experimentRecordServiceImpl.getById(id);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        experimentRecordServiceImpl.deleteById(id);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryListByEPId(@RequestParam("epId") Integer epId) {
        ListResult<ExperimentRecord> result = new ListResult<>();
        result.returnSuccess(experimentRecordServiceImpl.getListByEPId(epId));
        return (JSON) JSON.toJSON(result);
    }
}
