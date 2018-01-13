package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.dto.MessageListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by GJW on 2018/1/5.
 */
public interface ExperimentMessageService extends BaseServiceTemplate<ExperimentMessage> {

    int addMessage(ExperimentMessage message);

    int getUnreadCount(int userId, String conversationId);

    List<ExperimentMessage> getReceiveList(int toId, int state, int offset, int limit);

    void updateMessage(int id, int hasRead);

    List<MessageListDTO> getReceiveListDTO(int toId, int state, int offset, int limit);
}
