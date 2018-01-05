package com.example.demo.dao;

import com.example.demo.base.BaseMapperTemplate;
import com.example.demo.model.ExperimentMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by GJW on 2018/1/4.
 */
public interface ExperimentMessageMapper extends BaseMapperTemplate<ExperimentMessage> {

    /**
     * 添加message
     * @param message
     * @return
     */
    int addMessage(ExperimentMessage message);

    /**
     * 获取对话信息
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    List<ExperimentMessage> getConversitionDetail(@Param("conversationId") String  conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取用户消息列表
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<ExperimentMessage> getConversitionList(@Param("userId") int  userId,
                                      @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取消息数量
     * @param entityId
     * @param entityType
     * @return
     */
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    /**
     * 获取未读消息数
     * @param userId
     * @param conversationId
     * @return
     */
    int getUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    /**
     * 更新消息阅读状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
