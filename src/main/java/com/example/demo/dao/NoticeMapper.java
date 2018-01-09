package com.example.demo.dao;

import com.example.demo.model.Notice;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> getNoticeList(int offset, int limit);

    List<Notice> getMessageNoticeList(int offset, int limit);

}