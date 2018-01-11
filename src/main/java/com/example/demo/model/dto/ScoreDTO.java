package com.example.demo.model.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

@Data
public class ScoreDTO {
    private String idNumber;

    private String studentName;

    private String epFileUrl;

    private String epFileName;

    private Integer Score;

    private String Comment;

    private Integer StudentId;

    private Integer eprecordId;
}
