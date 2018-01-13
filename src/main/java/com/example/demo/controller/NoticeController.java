package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Notice;
import com.example.demo.model.User;
import com.example.demo.model.dto.NoticeDTO;
import com.example.demo.service.NoticeService;
import com.example.demo.service.UserService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.Result;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    private static final int PAGE_SIZE = 10;
    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public JSON getNoticeList(HttpServletRequest request) {
        int page = StringUtil.getInteger(request.getParameter("page"));
        ListResult<NoticeDTO> listResult = new ListResult<>();
        try {
            List<NoticeDTO> dtos = noticeService.getNoticeDTO(page*PAGE_SIZE, PAGE_SIZE);
            listResult.returnSuccess(dtos);
        } catch (Exception e) {
            LOGGER.error("获取公告列表失败" + e);
            listResult.returnError("获取公告列表失败");
        } finally {
            return (JSON) JSON.toJSON(listResult);
        }
    }



    @RequestMapping(value = "send", method = {RequestMethod.POST})
    @ResponseBody
    public JSON sendNotice(@RequestParam("content") String content, HttpServletRequest request) {
        SingleResult<Notice> result = new SingleResult<>();
        try {
            String title = request.getParameter("title");
            User user = SessionUtil.getUser(request.getSession());
            Notice notice = new Notice();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setCreateId(user.getId());
            notice.setEpId(0);
            notice.setCreateTime(new Date());
            noticeService.add(notice);
            result.returnSuccess(notice);
        }catch (Exception e) {
            LOGGER.error("发布公告失败" + e);
            result.returnError("发布公告失败");
        } finally {
            return (JSON) JSON.toJSON(result);
        }
    }

    @RequestMapping(value = "detail", method = {RequestMethod.GET})
    @ResponseBody
    public JSON getNoticeDetail(@RequestParam("id") Integer id) {
        SingleResult<Notice> result = new SingleResult<>();
        try {
            Notice notice = noticeService.getById(id);
            if(notice == null) {
                result.returnError("公告Id错误");
                return (JSON) JSON.toJSON(result);
            }
            result.returnSuccess(notice);
        } catch (Exception e) {
            LOGGER.error("获取公告详情失败" + e);
            result.returnError("获取公告详情失败");
        } finally {
            return (JSON) JSON.toJSON(result);
        }
    }
}

