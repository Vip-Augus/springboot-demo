package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.model.User;
import com.example.demo.service.ExperimentDetailService;
import com.example.demo.service.FileManageService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.file.UploadObject;
import com.example.demo.util.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;

/**
 * 实验课作业记录
 * Author by JingQ on 2018/1/6
 */
@RestController
@RequestMapping("epDetail")
public class ExperimentDetailController {

    private static final String BASE_DIR = "epDetail/";

    @Autowired
    private FileManageService fileManageServiceImpl;

    @Autowired
    private ExperimentDetailService experimentDetailServiceImpl;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addRecord(@RequestParam("epId") Integer epId, @RequestParam("epRecordId") Integer epRecordId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        User user;
        String url = null;
        try {
            user = SessionUtil.getUser(request.getSession());
            url = fileManageServiceImpl.upload(file, BASE_DIR+"/"+epRecordId+"/");
        } catch (Exception e) {
            return (JSON) JSON.toJSON(null);
        }
        ExperimentDetail record = convertDetail(epId, epRecordId, user.getId(), url, file.getOriginalFilename());
        result.returnSuccess(experimentDetailServiceImpl.add(record));
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateRecord(@RequestBody ExperimentDetail record, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        experimentDetailServiceImpl.update(record);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        ExperimentDetail record = experimentDetailServiceImpl.getById(id);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        experimentDetailServiceImpl.deleteById(id);
    }

    private ExperimentDetail convertDetail(Integer epId, Integer epRecordId, Integer userId, String fileUrl, String fileName) {
        ExperimentDetail detail = new ExperimentDetail();
        detail.setEpId(epId);
        detail.setEpRecordId(epRecordId);
        detail.setUserId(userId);
        detail.setEpFileName(fileName);
        detail.setEpFileUrl(fileUrl);
        return detail;
    }

}
