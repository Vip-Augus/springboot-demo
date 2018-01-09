package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.model.User;
import com.example.demo.model.ViewObject;
import com.example.demo.model.dto.MessageListDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ExperimentMessageService;
import com.example.demo.service.ExperimentService;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.service.UserService;
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
@RequestMapping(value = "/msg")
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

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public JSON getConversationDetail(@RequestParam("id") Integer id) {
        SingleResult<ExperimentMessage> result = new SingleResult<>();
        try {
            ExperimentMessage message = messageService.getById(id);
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getConversationList(HttpServletRequest request) {
        ListResult<MessageListDTO> result = new ListResult<>();
        try {
            int page = StringUtil.getInteger(request.getParameter("page"));
            User localUser = SessionUtil.getUser(request.getSession());
            int localUserId = localUser.getId();
            List<ExperimentMessage> messageList = Lists.newArrayList();
            if(localUser.getType() == UserType.STUDENT.getCode()) {
                messageList = messageService.getReceiveList(localUserId, page * PAGE_SIZE, 10);
            }else {
                //TODO:按epId区分
                messageList = messageService.getSendList(localUserId, page * PAGE_SIZE, 10);
            }
            List<MessageListDTO> dtos = getMessageListDTO(messageList, localUser);
            result.returnSuccess(dtos);
        } catch (Exception e) {
            result.returnError("查询消息列表失败");
            LOGGER.error("/msg/list", e);
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    private List<MessageListDTO> getMessageListDTO(List<ExperimentMessage> messageList, User user) {
        List<MessageListDTO> dtos = Lists.newArrayList();
        for (ExperimentMessage message : messageList) {
            MessageListDTO dto = new MessageListDTO();
            dto.setId(message.getId());
            dto.setTitle(message.getTitle());
            dto.setContent(message.getContent());
            dto.setCreatedDate(message.getCreatedDate());
            dto.setEqName(experimentService.getById(message.getEpId()).getName());
            dto.setSendUserName(userService.getById(message.getFromId()).getName());
            dto.setHasRead(message.getHasRead());
            dtos.add(dto);
        }
        return dtos;
    }

    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    @ResponseBody
    public JSON addMessage(@RequestParam("eqId") int eqId,
                             @RequestParam("content") String content, HttpServletRequest request) {
        SingleResult<String> result = new SingleResult<>();
        try {
            String title = request.getParameter("title");
            User user = SessionUtil.getUser(request.getSession());
            List<Integer> studentIds = experimentUserService.getUserIdsByEpId(eqId);
            new Thread() {
                @Override
                public void run() {
                    for(Integer id : studentIds) {
                        ExperimentMessage message = new ExperimentMessage();
                        message.setFromId(user.getId());
                        message.setToId(user.getId());
                        message.setEpId(eqId);
                        message.setTitle(title);
                        message.setContent(content);
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
}
