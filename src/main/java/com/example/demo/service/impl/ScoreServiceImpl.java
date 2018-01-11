package com.example.demo.service.impl;

import com.example.demo.dao.ScoreMapper;
import com.example.demo.model.Score;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10 0010.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;
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
}
