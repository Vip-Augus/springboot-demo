package com.example.demo.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

@Data
public class ScoresDTO {
    Integer count;
    List<ScoreDTO> scoreList;
}
