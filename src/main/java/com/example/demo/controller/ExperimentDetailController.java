package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.model.Score;
import com.example.demo.model.User;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ExperimentDetailService;
import com.example.demo.service.FileManageService;
import com.example.demo.service.ScoreService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 实验课作业记录
 * Author by JingQ on 2018/1/6
 */
@Slf4j
@RestController
@RequestMapping("/web/epDetail")
public class ExperimentDetailController {

    private static final String BASE_DIR = "epDetail/";

    @Autowired
    private FileManageService fileManageServiceImpl;

    @Autowired
    private ExperimentDetailService experimentDetailServiceImpl;

    @Autowired
    private ScoreService scoreServiceImpl;

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
            log.error("上传失败: ", e);
            return (JSON) JSON.toJSON(e.getMessage());
        }
        ExperimentDetail record = convertDetail(epId, epRecordId, user.getName(), user.getId(), url, file.getOriginalFilename());
        insertScore(epRecordId, user.getId());
        result.returnSuccess(experimentDetailServiceImpl.add(record));
        log.info("上传成功: epDetailId:", record.getId());
        return (JSON) JSON.toJSON(result);
    }

    /**
     * 测试添加Score
     * @param record
     * @param request
     * @return
     */
    /*@RequestMapping(value = "addScore", method = RequestMethod.POST)
    @ResponseBody
    public JSON addScore(@RequestParam("epRecordId") Integer epRecordId, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        User user;
        String url = null;
        try {
            user = SessionUtil.getUser(request.getSession());
            insertScore(epRecordId, user.getId());
        } catch (Exception e) {
            log.error("上传失败: ", e);
            return (JSON) JSON.toJSON(e.getMessage());
        }
        result.returnSuccess(null);
        //log.info("上传成功: epDetailId:", record.getId());
        return (JSON) JSON.toJSON(result);
    }*/

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateRecord(@RequestBody ExperimentDetail record, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        try {
            experimentDetailServiceImpl.update(record);
            result.returnSuccess(record);
        } catch (Exception e) {
            log.error("更新失败: ", e);
            return (JSON) JSON.toJSON(e.getMessage());
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentDetail> result = new SingleResult<>();
        ExperimentDetail record = experimentDetailServiceImpl.getById(id);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            result.returnSuccess(experimentDetailServiceImpl.deleteById(id));
        } catch (Exception e) {
            log.error("删除失败: ", e);
            return (JSON) JSON.toJSON(e.getMessage());
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryListByEPId(@RequestParam("epRecordId") Integer epRecordId, HttpServletRequest request) {
        ListResult<ExperimentDetail> result = new ListResult<>();
        User user;
        String url = null;
        try {
            user = SessionUtil.getUser(request.getSession());
        } catch (Exception e) {
            log.error("查询用户失败: ", e);
            return (JSON) JSON.toJSON(e.getMessage());
        }
        try {
            Integer userId = UserType.TEACHER.getCode().equals(user.getType()) ? null : user.getId();
            result.returnSuccess(experimentDetailServiceImpl.getDetailsByEPRecordId(epRecordId, userId));
        } catch (Exception e) {
            log.error("查询上传作业列表失败:", epRecordId, e);
            result.returnError("查询实验内容列表失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    private ExperimentDetail convertDetail(Integer epId, Integer epRecordId, String userName, Integer userId, String fileUrl, String fileName) {
        ExperimentDetail detail = new ExperimentDetail();
        detail.setEpId(epId);
        detail.setEpRecordId(epRecordId);
        detail.setUserId(userId);
        detail.setEpFileName(fileName);
        detail.setUploadName(userName);
        detail.setEpFileUrl(fileUrl);
        return detail;
    }

    private void insertScore(Integer epRecordId, Integer stuId) {
        Score score = new Score();
        score.setEprecordId(epRecordId);
        score.setStudentId(stuId);
        scoreServiceImpl.add(score);
    }
}
