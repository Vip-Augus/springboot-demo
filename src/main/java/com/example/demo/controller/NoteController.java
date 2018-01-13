package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Note;
import com.example.demo.model.Notice;
import com.example.demo.model.User;
import com.example.demo.model.dto.NoticeDTO;
import com.example.demo.service.NoteService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/12
 */
@RestController
@RequestMapping(value = "/note")
public class NoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    @Autowired
    NoteService noteService;

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public JSON getNoticeList(HttpServletRequest request) {
        int page = StringUtil.getInteger(request.getParameter("page"));
        ListResult<Note> listResult = new ListResult<>();
        try {
            User user = SessionUtil.getUser(request.getSession());
            List<Note> notes = noteService.getPlanList(user.getId());
            listResult.returnSuccess(notes);
        } catch (Exception e) {
            LOGGER.error("获取便签列表失败" , e);
            listResult.returnError("获取便签列表失败");
        } finally {
            return (JSON) JSON.toJSON(listResult);
        }
    }

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ResponseBody
    public JSON addNote(HttpServletRequest request) {
        String content = request.getParameter("content");
        SingleResult<Note> listResult = new SingleResult<>();
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(request.getParameter("plandate"));
            User user = SessionUtil.getUser(request.getSession());
            Note note = new Note();
            note.setContent(content);
            note.setCreateTime(new Date());
            note.setPlanTime(date);
            note.setUserId(user.getId());
            noteService.add(note);
            listResult.returnSuccess(note);
        } catch (Exception e) {
            LOGGER.error("添加便签失败" , e);
            listResult.returnError("添加便签失败");
        } finally {
            return (JSON) JSON.toJSON(listResult);
        }
    }

}
