package com.example.demo.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Data
public class Scores {
    private int submit;
    private int epRecordId;
    private List<Score> scoreList;
}
