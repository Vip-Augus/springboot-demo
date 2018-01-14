package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentMessageMapper;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.dto.MessageListDTO;
import com.example.demo.service.ExperimentMessageService;
import com.example.demo.service.ExperimentService;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by GJW on 2018/1/5.
 */
@Service
public class ExperimentMessageServiceImpl implements ExperimentMessageService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExperimentMessageServiceImpl.class);
    @Autowired
    ExperimentMessageMapper messageDAO;

    @Autowired
    ExperimentService experimentService;

    @Autowired
    UserService userService;

    public int addMessage(ExperimentMessage message) {
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

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
    public List<ExperimentMessage> getReceiveList(int toId, int state, int offset, int limit) {
        List<ExperimentMessage> messages = messageDAO.getReceiveMessageList(toId, state, offset, limit);
        if(messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }

    @Override
    public void updateMessage(int id, int hasRead) {
        if(id <= 0 ||(hasRead != 0 && hasRead != 1)) {
            return;
        }
        messageDAO.updateStatus(id, hasRead);
    }

    @Override
    public List<MessageListDTO> getReceiveListDTO(int toId, int state, int offset, int limit) {
        List<ExperimentMessage> messages = messageDAO.getReceiveMessageList(toId, state, offset, limit);
        if(messages == null) {
            return Collections.emptyList();
        }
        return getMessageListDTO(messages);
    }

    private List<MessageListDTO> getMessageListDTO(List<ExperimentMessage> messageList) {
        List<MessageListDTO> dtos = Lists.newArrayList();
        for (ExperimentMessage message : messageList) {
            MessageListDTO dto = new MessageListDTO();
            dto.setId(message.getId());
            dto.setTitle(message.getTitle());
            dto.setContent(message.getContent());
            dto.setCreatedDate(message.getCreatedDate());
            dto.setEpName(experimentService.getById(message.getEpId()).getName());
            dto.setSendUserName(userService.getById(message.getFromId()).getName());
            dto.setHasRead(message.getHasRead());
            dtos.add(dto);
        }
        return dtos;
    }
}
