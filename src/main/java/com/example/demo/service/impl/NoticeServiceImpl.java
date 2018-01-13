package com.example.demo.service.impl;

import com.example.demo.dao.NoticeMapper;
import com.example.demo.model.Notice;
import com.example.demo.model.dto.NoticeDTO;
import com.example.demo.service.NoticeService;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    UserService userService;
    @Override
    public Notice getById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Notice record) {
        return noticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Notice add(Notice record) {
        noticeMapper.insert(record);
        return record;
    }

    @Override
    public List<Notice> getList(int offset, int limit) {
        List<Notice> notices = noticeMapper.getNoticeList(offset, limit);
        if(CollectionUtils.isEmpty(notices)) {
            return Collections.emptyList();
        }
        return notices;
    }

    @Override
    public List<Notice> getMessageList(int userId, int offset, int limit) {
        List<Notice> notices = noticeMapper.getMessageNoticeList(userId, offset, limit);
        if(CollectionUtils.isEmpty(notices)) {
            return Collections.emptyList();
        }
        return notices;
    }

    public List<NoticeDTO> getNoticeDTO(int offset, int limit) {
        List<Notice> notices = getList(offset, limit);
        if(CollectionUtils.isEmpty(notices)) {
            return Collections.emptyList();
        }
        List<NoticeDTO> dtos = Lists.newArrayList();
        for(Notice notice : notices) {
            NoticeDTO dto = new NoticeDTO();
            dto.setId(notice.getId());
            dto.setTitle(notice.getTitle());
            dto.setContent(notice.getContent());
            dto.setCreateTime(notice.getCreateTime());
            dto.setCreateUserName(userService.getById(notice.getCreateId()).getName());
            dto.setCreateId(notice.getCreateId());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<NoticeDTO> getMessageNoticeDTO(int userId, int offset, int limit) {
        List<Notice> notices = getMessageList(userId, offset, limit);
        if(CollectionUtils.isEmpty(notices)) {
            return Collections.emptyList();
        }
        List<NoticeDTO> dtos = Lists.newArrayList();
        for(Notice notice : notices) {
            NoticeDTO dto = new NoticeDTO();
            dto.setId(notice.getId());
            dto.setTitle(notice.getTitle());
            dto.setContent(notice.getContent());
            dto.setCreateTime(notice.getCreateTime());
            dto.setCreateUserName(userService.getById(notice.getCreateId()).getName());
            dto.setCreateId(notice.getCreateId());
            dtos.add(dto);
        }
        return dtos;
    }
}
