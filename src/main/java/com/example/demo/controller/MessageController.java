package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.User;
import com.example.demo.model.ViewObject;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.ExperimentMessageService;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.service.UserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.result.Result;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GJW on 2018/1/5.
 */
@RestController
@RequestMapping(value = "/msg")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    ExperimentMessageService messageService;

    @Autowired
    ExperimentUserService experimentUserService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<ExperimentMessage> messageList = messageService.getConversitionDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for (ExperimentMessage message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getById(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);

        } catch (Exception e) {
            LOGGER.error("获取消息失败：", e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getConversationList(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User localUser = SessionUtil.getUser(session);
            int localUserId = localUser.getId();
            List<ExperimentMessage> messageList = messageService.getConversitionlist(localUserId, 0, 10);
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            for (ExperimentMessage message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", message);
                vo.set("unread", messageService.getUnreadCount(localUserId, message.getConversationId()));
                int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
                vo.set("user", userService.getById(targetId));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            LOGGER.error("/msg/list", e);
        }
        return "letter";
    }


    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("eqId") int eqId,
                             @RequestParam("content") String content, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            List<Integer> studentIds = experimentUserService.getUserIdsByEpId(eqId);
            ExperimentMessage message = new ExperimentMessage();
            message.setContent(content);
            message.setFromId(user.getId());
            message.setToId(user.getId());
            message.setCreatedDate(new Date());
            message.setConversationId(message.getConversationId());
            messageService.addMessage(message);
            /*if (commentService.addComment(comment) > 0) {
                return HowtoseUtil.getJsonString(0);
            }*/
            return "200";

        } catch (Exception e) {
            LOGGER.error(" 发送消息失败：", e.getMessage());
        }
        return "502";
    }
}
