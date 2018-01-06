package com.example.demo.model;

import com.alibaba.druid.util.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by GJW on 2018/1/4.
 */
@Data
public class ExperimentMessage {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public String getConversationId() {
        if(!StringUtils.isEmpty(conversationId)){
            return conversationId;
        }
        if(fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        return String.format("%d_%d", toId, fromId);
    }
}
