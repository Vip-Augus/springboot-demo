package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Notice;
import com.example.demo.model.dto.NoticeDTO;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
public interface NoticeService  extends BaseServiceTemplate<Notice> {
    List<Notice> getList(int offset, int limit);

    List<Notice> getMessageList(int userId, int offset, int limit);

    List<NoticeDTO> getNoticeDTO(int offset, int limit);

    List<NoticeDTO> getMessageNoticeDTO(int userId, int offset, int limit);
}
