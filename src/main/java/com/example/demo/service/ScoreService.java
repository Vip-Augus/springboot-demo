package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Score;
import com.example.demo.model.dto.ScoreDTO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10 0010.
 */
public interface ScoreService extends BaseServiceTemplate<Score> {
    /**
     * 分页获取列表
     * @param eprecordId
     * @param offset
     * @param limit
     * @return
     */
    List<Score> getList(Integer eprecordId, int offset, int limit);

    /**
     * 不分页获取列表
     * @param eprecordId
     * @return
     */
    List<Score> getList(Integer eprecordId);

    /**
     * 获取单个分数数据
     * @param eprecordId
     * @param stuId
     * @return
     */
    Score get(Integer eprecordId, Integer stuId);

    /**
     * 获取总条数
     * @param eprecordId
     * @return
     */
    int getCount(Integer eprecordId);

    /**
     * 根据老师ID和条数查待批阅报告
     * @param teacherId
     * @param limit
     * @return
     */
    List<ScoreDTO> getByTeacherId(Integer teacherId, Integer limit);
}
