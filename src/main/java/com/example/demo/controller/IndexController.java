package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Notice;
import com.example.demo.model.User;
import com.example.demo.model.dto.NoticeDTO;
import com.example.demo.model.dto.index.IndexDTO;
import com.example.demo.model.dto.index.ManagerIndexDTO;
import com.example.demo.model.dto.index.StudentIndexDTO;
import com.example.demo.model.dto.index.TeacherIndexDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.*;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/12
 */
@RestController
@RequestMapping(value = "/web/index")
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    NoteService noteService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    ExperimentMessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    CourseReviewService courseReviewService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSON getIndex(HttpServletRequest request) {
        SingleResult<IndexDTO> result = new SingleResult<>();
        try {

            User user = SessionUtil.getUser(request.getSession());
            IndexDTO dto = new IndexDTO();
            if(user.getType().equals(UserType.STUDENT.getCode())) {
                dto = getStudentIndex(user);
            } else if (user.getType().equals(UserType.TEACHER.getCode())) {
                dto = getTeacherIndex(user);
            } else if (user.getType().equals(UserType.MANAGE.getCode())) {
                dto = getManagerIndex(user);
            }
            result.returnSuccess(dto);

        } catch (Exception e) {
            LOGGER.error("获取首页数据失败", e);
            result.returnError("获取首页数据失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    private IndexDTO getStudentIndex(User user) {
        StudentIndexDTO dto = new StudentIndexDTO();
        dto.setNoteList(noteService.getPlanList(user.getId()));
        dto.setNoticeList(noticeService.getNoticeDTO(0, 10));
        dto.setMessageList(messageService.getReceiveListDTO(user.getId(), 0, 0, 10));
        return dto;
    }

    private IndexDTO getTeacherIndex(User user) {
        TeacherIndexDTO dto = new TeacherIndexDTO();
        dto.setNoteList(noteService.getPlanList(user.getId()));
        dto.setNoticeList(noticeService.getNoticeDTO(0, 10));
        dto.setScoreLsit(scoreService.getByTeacherId(user.getId(), 10));
        return dto;
    }

    private IndexDTO getManagerIndex(User user) {
        ManagerIndexDTO dto = new ManagerIndexDTO();
        dto.setNoteList(noteService.getPlanList(user.getId()));
        dto.setNoticeList(noticeService.getNoticeDTO(0, 10));
        dto.setScoreLsit(scoreService.getByTeacherId(user.getId(), 10));
        dto.setCourseReviewList(courseReviewService.getListByState(0));
        return dto;
    }


}
