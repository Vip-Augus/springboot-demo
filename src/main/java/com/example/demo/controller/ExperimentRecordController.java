package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.service.ExperimentRecordService;
import com.example.demo.service.FileManageService;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 实验课记录控制器
 * Author by JingQ on 2018/1/6
 */
@Slf4j
@RestController
@RequestMapping("/epRecord")
public class ExperimentRecordController {


    private static final String BASE_DIR = "record/";
    @Autowired
    private ExperimentRecordService experimentRecordServiceImpl;

    @Autowired
    private FileManageService fileManageServiceImpl;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addRecord(@RequestParam("epName") String epName,
                          @RequestParam("epId") Integer epId,
                          @RequestParam("uploadEndTime") String uploadEndTime,
                          @RequestParam("classroomId") Integer classroomId,
                          @RequestParam("file") MultipartFile file,
                          HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        if (file.isEmpty()) {
            log.error("未选择材料,发布实验失败: ", epId, epName);
            result.returnError("未选择材料,发布实验失败");
        } else {
            try {
                StringBuilder filePath = new StringBuilder(BASE_DIR);
                filePath.append(epName).append("/").append(epId).append("/");
                String url = fileManageServiceImpl.upload(file, filePath.toString());
                ExperimentRecord record = convertRecord(epName, epId, uploadEndTime, url, classroomId);
                result.returnSuccess(experimentRecordServiceImpl.add(record));
                log.info("成功添加实验记录:", record.getId());
            } catch (Exception e) {
                log.error("发布实验失败: ", epName, e);
                result.returnError("发布实验失败");
            }
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateRecord(@RequestBody ExperimentRecord record, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        try {
            experimentRecordServiceImpl.update(record);
            result.returnSuccess(record);
            log.info("成功修改实验记录: ", record.getId());
        } catch (Exception e) {
            log.error("修改实验内容失败:", record.getId(), e);
            result.returnError("修改实验内容失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        try {
            ExperimentRecord record = experimentRecordServiceImpl.getById(id);
            result.returnSuccess(record);
        } catch (Exception e) {
            log.error("查询实验内容失败:", id, e);
            result.returnError("查询实验内容失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            result.returnSuccess(experimentRecordServiceImpl.deleteById(id));
            log.error("删除实验内容成功:", id);
        } catch (Exception e) {
            log.error("删除实验内容失败:", id, e);
            result.returnError("删除实验内容失败");
        }
        return (JSON) JSON.toJSON(result);    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryListByEPId(@RequestParam("epId") Integer epId) {
        ListResult<ExperimentRecord> result = new ListResult<>();
        try {
            result.returnSuccess(experimentRecordServiceImpl.getListByEPId(epId));
        } catch (Exception e) {
            log.error("查询实验内容列表失败:", epId, e);
            result.returnError("查询实验内容列表失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    private ExperimentRecord convertRecord(String epName, Integer epId, String uploadEndTime, String fileUrl, Integer classroomId) {
        ExperimentRecord record = new ExperimentRecord();
        record.setEpId(epId);
        record.setEpName(epName);
        record.setUploadEndTime(uploadEndTime);
        record.setEpFileUrl(fileUrl);
        record.setClassroomId(classroomId);
        return record;
    }
}
