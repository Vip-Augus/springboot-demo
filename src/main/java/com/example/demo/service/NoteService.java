package com.example.demo.service;

import com.example.demo.base.BaseServiceTemplate;
import com.example.demo.model.Note;

import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/13
 */
public interface NoteService extends BaseServiceTemplate<Note> {
    List<Note> getPlanList(int userId);
}
