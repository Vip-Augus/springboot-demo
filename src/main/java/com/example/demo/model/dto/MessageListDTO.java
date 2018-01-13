package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by GJW on 2018/1/7.
 * 这名字起得真矬..
 */
@Data
public class MessageListDTO {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    private String eqName;
    private String sendUserName;
    private int hasRead;
}
