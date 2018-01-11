package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.model.Score;
import com.example.demo.model.Scores;
import com.example.demo.model.dto.ScoreDTO;
import com.example.demo.model.dto.ScoresDTO;
import com.example.demo.service.ExperimentDetailService;
import com.example.demo.service.ScoreService;
import com.example.demo.service.UserService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.result.SingleResult;
import com.google.common.collect.Lists;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9 0009.
 */
@Controller
@RequestMapping(value = "/score")
public class ScoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    private static final int PAGE_SIZE = 10;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExperimentDetailService experimentDetailService;

    /**
     * 获取分数列表
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getScoreList(HttpServletRequest request) {
        int page = StringUtil.getInteger(request.getParameter("page"));
        SingleResult<ScoresDTO> result = new SingleResult<>();
        try {
            //Integer teacherId = SessionUtil.getUser(request.getSession()).getId();
            Integer eprecordId = StringUtil.getInteger(request.getParameter("eprecordId"));
            ScoresDTO scoresDTO = new ScoresDTO();
            scoresDTO.setCount(scoreService.getCount(eprecordId));
            List<Score> scores = scoreService.getList(eprecordId,page*PAGE_SIZE, PAGE_SIZE);
            List<ScoreDTO> dtos = getScoreList(scores, eprecordId);
            scoresDTO.setScoreList(dtos);
            result.returnSuccess(scoresDTO);
        } catch (Exception e) {
            LOGGER.error("获取分数列表失败" + e);
            result.returnError("获取分数列表失败");
        } finally {
            return (JSON) JSON.toJSON(result);
        }
    }

    private List<ScoreDTO> getScoreList(List<Score> scores, Integer eprecordId) {
        if(CollectionUtils.isEmpty(scores)) {
            return Collections.emptyList();
        }
        List<ScoreDTO> dtos = Lists.newArrayList();
        for(Score score : scores) {
            ScoreDTO dto = new ScoreDTO();
            Integer studentId = score.getStudentId();
            dto.setEprecordId(score.getEprecordId());
            dto.setStudentId(studentId);
            dto.setIdNumber(userService.getById(studentId).getIdNumber());
            dto.setStudentName(userService.getById(studentId).getName());
            List<ExperimentDetail> experimentDetailList = experimentDetailService.getDetailsByEPRecordId(eprecordId, studentId);
            ExperimentDetail experimentDetail = experimentDetailList.get(experimentDetailList.size()-1);
            dto.setEpFileUrl(experimentDetail.getEpFileUrl());
            dto.setEpFileName(experimentDetail.getEpFileName());
            dto.setScore(score.getScore());
            dto.setComment(score.getComment());
            dtos.add(dto);
        }
        return dtos;
    }

    @RequestMapping(value = "mark", method = RequestMethod.POST)
    @ResponseBody
    public JSON modify(@RequestBody Scores scores, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            Params params = new Params();
            params.setEpRecordId(scores.getEpRecordId());
            params.setTeaId(SessionUtil.getUser(request.getSession()).getId());
            params.setScoreList(scores.getScoreList());
            params.setScoreListSQL(scoreService.getList(params.epRecordId));
            params.setSubmit(scores.getSubmit());
            if(params.scoreListSQL.get(0).getSubmit() == 1) {
                result.returnError("已经批过");
            }
            else if(params.getSubmit() == 1) {
                boolean flag = true;
                List<Integer> stuIds = new ArrayList<Integer>();
                for(Score score : params.scoreList) {
                    if(score.getScore() == null) {
                        flag = false;
                        result.returnError("没有完成批阅");
                        break;
                    }
                    stuIds.add(score.getStudentId());
                }
                if(flag) {
                    for (Score scoreSQL : params.scoreListSQL) {
                        if(!stuIds.contains(scoreSQL.getStudentId())) {
                            if(scoreSQL.getScore() == null) {
                                flag = false;
                                result.returnError("没有完成批阅");
                                break;
                            }
                        }
                    }
                    if(flag){
                        save(params);
                        result.returnSuccess(null);
                    }
                }
            }
            else {
                save(params);
                result.returnSuccess(null);
            }
        } catch (Exception e) {
            LOGGER.error("获取分数列表失败" + e);
            result.returnError("获取分数列表失败");
        } finally {
            return (JSON) JSON.toJSON(result);
        }
    }

    @Data
    private class Params
    {
        private Integer epRecordId;
        private Integer teaId;
        private Integer submit;
        private List<Score> scoreList;
        private List<Score> scoreListSQL;
    }

    private void save(Params params) {
        for (Score score : params.scoreList) {
            Score scoreSQL = scoreService.get(params.epRecordId, score.getStudentId());
            if(scoreSQL.getScore() != score.getScore() || scoreSQL.getComment() != score.getComment()) {
                score.setSubmit(params.submit);
                score.setTeacherId(params.teaId);
                scoreService.update(score);
            }
        }
    }

}
