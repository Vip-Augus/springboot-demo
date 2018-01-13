package com.example.demo.dao;

import com.example.demo.model.Note;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    int insertSelective(Note record);

    Note selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);

    List<Note> getPlanList(int userId);
}