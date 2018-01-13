package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.*;
import com.example.demo.model.dto.MessageListDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.*;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.Result;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by GJW on 2018/1/5.
 */
@RestController
@RequestMapping(value = "/web/msg")
public class MessageController {

    private static final int PAGE_SIZE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    ExperimentMessageService messageService;

    @Autowired
    ExperimentUserService experimentUserService;

    @Autowired
    ExperimentService experimentService;

    @Autowired
    UserService userService;

    @Autowired
    NoticeService noticeService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public JSON getConversationDetail(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentMessage> result = new SingleResult<>();
        try {
            User user = SessionUtil.getUser(request.getSession());
            ExperimentMessage message;
            if(user.getType().equals( UserType.STUDENT)) {
                message = messageService.getById(id);
                messageService.updateMessage(message.getId(), 1);
            } else {
                Notice notice = noticeService.getById(id);
                message = changeNoticeToMessage(notice);
            }
            if(message == null) {
                result.returnError("消息Id错误");
                return (JSON) JSON.toJSON(result);
            }
            result.returnSuccess(message);
            return (JSON) JSON.toJSON(result);

        } catch (Exception e) {
            LOGGER.error("获取消息失败：", e.getMessage());
            result.returnError("获取消息失败");
            return (JSON) JSON.toJSON(result);
        }
    }

    private ExperimentMessage changeNoticeToMessage(Notice notice) {
        ExperimentMessage message = new ExperimentMessage();
        message.setHasRead(1);
        message.setContent(notice.getContent());
        message.setTitle(notice.getTitle());
        message.setCreatedDate(notice.getCreateTime());
        return message;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getTeacherList(HttpServletRequest request) {
        ListResult<MessageListDTO> result = new ListResult<>();
        try {
            int state = StringUtil.getInteger(request.getParameter("state"));
            int page = StringUtil.getInteger(request.getParameter("page"));
            User localUser = SessionUtil.getUser(request.getSession());
            int localUserId = localUser.getId();
            List<MessageListDTO> dtos;
            if(localUser.getType().equals(UserType.STUDENT.getCode())) {
                dtos = messageService.getReceiveListDTO(localUserId, state,page * PAGE_SIZE, PAGE_SIZE);
            } else {
                List<Notice> notices = noticeService.getMessageList(localUserId, page * PAGE_SIZE, PAGE_SIZE);
                dtos = getMessageListDTOFromNotice(notices);
            }
            result.returnSuccess(dtos);
        } catch (Exception e) {
            result.returnError("查询消息列表失败");
            LOGGER.error("list", e);
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    private List<MessageListDTO> getMessageListDTOFromNotice(List<Notice> noticeList) {
        List<MessageListDTO> dtos = Lists.newArrayList();
        for (Notice notice : noticeList) {
            MessageListDTO dto = new MessageListDTO();
            dto.setId(notice.getId());
            dto.setTitle(notice.getTitle());
            dto.setContent(notice.getContent());
            dto.setCreatedDate(notice.getCreateTime());
            dto.setEqName(experimentService.getById(notice.getEpId()).getName());
            dto.setSendUserName(userService.getById(notice.getCreateId()).getName());
            dto.setHasRead(1);
            dtos.add(dto);
        }
        return dtos;
    }

    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    @ResponseBody
    public JSON addMessage(@RequestParam("epId") int epId,
                             @RequestParam("content") String content, HttpServletRequest request) {
        SingleResult<String> result = new SingleResult<>();
        try {
            String title = request.getParameter("title");
            User user = SessionUtil.getUser(request.getSession());
            Notice notice = new Notice();
            notice.setTitle(title);
            notice.setContent(content);
            notice.setCreateId(user.getId());
            notice.setEpId(epId);
            notice.setCreateTime(new Date());
            noticeService.add(notice);
            List<Integer> studentIds = experimentUserService.getUserIdsByEpId(epId);
            new Thread() {
                @Override
                public void run() {
                    for(Integer id : studentIds) {
                        ExperimentMessage message = new ExperimentMessage();
                        message.setFromId(user.getId());
                        message.setToId(id);
                        message.setEpId(epId);
                        message.setTitle(title);
                        message.setContent(content);
                        message.setHasRead(0);
                        message.setCreatedDate(new Date());
                        message.setConversationId(message.getConversationId());
                        messageService.addMessage(message);
                    }
                }
            }.start();
            result.returnSuccess("发送成功");
            return (JSON) JSON.toJSON(result);

        } catch (Exception e) {
            LOGGER.error(" 发送消息失败：", e.getMessage());
            result.returnSuccess("发送消息失败");
            return (JSON) JSON.toJSON(result);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteMessage(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            User user = SessionUtil.getUser(request.getSession());
            ExperimentMessage message;
            if(user.getType().equals( UserType.STUDENT)) {
                messageService.deleteById(id);
            } else {
                noticeService.deleteById(id);
            }

            return (JSON) JSON.toJSON(200);

        } catch (Exception e) {
            LOGGER.error("删除消息失败：", e.getMessage());
            result.returnError("删除消息失败");
            return (JSON) JSON.toJSON(result);
        }
    }
}
