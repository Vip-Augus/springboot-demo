package com.example.demo.dao;

import com.example.demo.model.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);

    List<Score> selectByEpId(@Param("eprecordId") Integer eprecordId, @Param("offset") int offset, @Param("limit") int limit);

    List<Score> selectByEpIdNoLim(@Param("eprecordId") Integer eprecordId);

    Score selectByEpIdAndStuId(@Param("eprecordId") Integer eprecordId, @Param("studentId") Integer studentId);

    Integer selectCount(@Param("eprecordId") Integer eprecordId);

    int updateByEprecordIdAndStuId(Score record);

    List<Score> selectByEpIdNoMark(@Param("eprecordId") Integer eprecordId, @Param("limit") int limit);
}