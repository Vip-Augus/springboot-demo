package com.example.demo.service.impl;

import com.example.demo.dao.ScoreMapper;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.model.ExperimentUser;
import com.example.demo.model.Score;
import com.example.demo.model.dto.ScoreDTO;
import com.example.demo.service.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10 0010.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ExperimentUserService experimentUserService;
    @Autowired
    UserService userService;
    @Autowired
    ExperimentDetailService experimentDetailService;
    @Autowired
    ExperimentRecordService experimentRecordService;
    @Override
    public Score getById(Integer id) {
        return null;
    }

    @Override
    public int update(Score score) {
        return scoreMapper.updateByEprecordIdAndStuId(score);
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Score add(Score score) {
        scoreMapper.insert(score);
        return score;
    }

    @Override
    public List<Score> getList(Integer eprecordId, int offset, int limit) {
        List<Score> scores = scoreMapper.selectByEpId(eprecordId, offset, limit);
        if(CollectionUtils.isEmpty(scores)) {
            return Collections.emptyList();
        }
        return scores;
    }

    @Override
    public List<Score> getList(Integer eprecordId) {
        List<Score> scores = scoreMapper.selectByEpIdNoLim(eprecordId);
        if(CollectionUtils.isEmpty(scores)) {
            return Collections.emptyList();
        }
        return scores;
    }

    @Override
    public Score get(Integer eprecordId, Integer stuId) {
        return scoreMapper.selectByEpIdAndStuId(eprecordId, stuId);
    }

    @Override
    public int getCount(Integer eprecordId) {
        return scoreMapper.selectCount(eprecordId);
    }

    @Override
    public List<ScoreDTO> getByTeacherId(Integer teacherId, Integer limit) {
        List<ScoreDTO> scoreDTOList = new ArrayList<ScoreDTO>();
        List<Score> scoreList = new ArrayList<Score>();
        List<Integer> epiDs = experimentUserService.getEPIDsByUserID(teacherId);
        for(Integer epId: epiDs) {
            List<ExperimentRecord> epRepords = experimentRecordService.getListByEPId(epId);
            for (ExperimentRecord eprecord : epRepords) {
                scoreList.addAll(scoreMapper.selectByEpIdNoMark(eprecord.getId(), limit));
                if(scoreList.size() >= limit) break;
            }
            if(scoreList.size() >= limit) break;
        }
        if(limit < scoreList.size()) {
            scoreDTOList = getScoreList(scoreList.subList(0, limit));
        }
        else {
            scoreDTOList = getScoreList(scoreList);
        }
        return scoreDTOList;
    }

    private List<ScoreDTO> getScoreList(List<Score> scores) {
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
            List<ExperimentDetail> experimentDetailList = experimentDetailService.getDetailsByEPRecordId(score.getEprecordId(), studentId);
            ExperimentDetail experimentDetail = experimentDetailList.get(experimentDetailList.size()-1);
            dto.setEpFileUrl(experimentDetail.getEpFileUrl());
            dto.setEpFileName(experimentDetail.getEpFileName());
            dto.setScore(score.getScore());
            dto.setComment(score.getComment());
            dtos.add(dto);
        }
        return dtos;
    }
}
