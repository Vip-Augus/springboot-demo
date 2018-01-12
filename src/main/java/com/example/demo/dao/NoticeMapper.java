package com.example.demo.dao;

import com.example.demo.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> getNoticeList(@Param("offset")int offset,@Param("limit") int limit);

    List<Notice> getMessageNoticeList(@Param("userId") int userId, @Param("offset")int offset,@Param("limit") int limit);

}