package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentMessageMapper;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.service.ExperimentMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by GJW on 2018/1/5.
 */
@Service
public class ExperimentMessageServiceImpl implements ExperimentMessageService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExperimentMessageServiceImpl.class);
    @Autowired
    ExperimentMessageMapper messageDAO;

    public List<ExperimentMessage> getConversitionDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversitionDetail(conversationId, offset, limit);
    }

    public List<ExperimentMessage> getConversitionlist(int userId, int offset, int limit) {
        return messageDAO.getConversitionList(userId, offset, limit);
    }

    public int addMessage(ExperimentMessage message) {
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    /*public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public boolean deleteComment(int id){
        return commentDAO.updateStatus( id, 1) > 0;
    }*/

    public int getUnreadCount(int userId, String conversationId){
        return messageDAO.getUnreadCount(userId, conversationId);
    }


    @Override
    public ExperimentMessage getById(Integer id) {
        return messageDAO.selectByPrimaryKey(id);
    }

    @Override
    public int update(ExperimentMessage record) {
        return messageDAO.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return messageDAO.deleteByPrimaryKey(id);
    }

    @Override
    public ExperimentMessage add(ExperimentMessage record) {
        messageDAO.insert(record);
        return record;
    }

    @Override
    public List<ExperimentMessage> getReceiveList(int toId, int offset, int limit) {
        List<ExperimentMessage> messages = messageDAO.getReceiveMessageList(toId, offset, limit);
        if(messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }


}
