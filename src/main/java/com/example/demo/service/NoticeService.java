package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Notice;

import java.util.List;

/**
 * Created by GJW on 2018/1/7.
 */
public interface NoticeService  extends BaseServiceTemplate<Notice> {
    List<Notice> getList(int offset, int limit);
}
