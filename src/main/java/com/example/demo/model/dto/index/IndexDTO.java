package com.example.demo.model.dto.index;

import com.example.demo.model.Note;
import com.example.demo.model.Notice;
import com.example.demo.model.dto.NoticeDTO;
import lombok.Data;

import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/13
 */
@Data
public class IndexDTO {
    protected List<NoticeDTO> noticeList;
    protected List<Note> noteList;
}
