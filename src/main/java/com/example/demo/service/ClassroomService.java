package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Classroom;
import com.example.demo.model.dto.ClassroomQueryParam;

import java.util.List;

/**
 * 实验室地点服务
 * Author by JingQ on 2018/1/4
 */
public interface ClassroomService extends BaseServiceTemplate<Classroom>{

    /**
     * 获取实验室列表
     * @param queryParam    查询条件
     * @return              实验室列表
     */
    List<Classroom> getList(ClassroomQueryParam queryParam);

}