package com.example.demo.model.dto;

import com.example.demo.base.BaseDTO;
import com.example.demo.model.User;
import lombok.Data;

import java.util.List;

/**
 * 实验课信息
 * Author by JingQ on 2018/1/2
 */
@Data
public class ExperimentDTO extends BaseDTO {

    private String name;

    private String briefIntroduction;

    private Integer classroomId;

    private String beginPeriod;

    private String endPeriod;

    private Integer courseId;

    private String tIds;

    private Integer day;

    private Integer classBegin;

    private Integer classEnd;

    private String joinEndTime;

    private List<User> teachers;

}
