package com.example.demo.service.impl;

import com.example.demo.dao.NoticeMapper;
import com.example.demo.model.Notice;
import com.example.demo.service.NoticeService;
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
}
