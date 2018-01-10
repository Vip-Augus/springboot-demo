package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.ExperimentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by GJW on 2018/1/5.
 */
public interface ExperimentMessageService extends BaseServiceTemplate<ExperimentMessage> {

    List<ExperimentMessage> getConversitionDetail(String conversationId, int offset, int limit);

    List<ExperimentMessage> getConversitionlist(int userId, int offset, int limit);

    int addMessage(ExperimentMessage message);

    int getUnreadCount(int userId, String conversationId);

    List<ExperimentMessage> getReceiveList(int toId, int offset, int limit);
}
