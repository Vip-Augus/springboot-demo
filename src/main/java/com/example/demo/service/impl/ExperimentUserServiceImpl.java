package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentUserMapper;
import com.example.demo.model.ExperimentUser;
import com.example.demo.service.ExperimentUserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentUserServiceImpl implements ExperimentUserService {

    @Autowired
    private ExperimentUserMapper experimentUserMapper;

    @Override
    public ExperimentUser getById(Integer id) {
        return experimentUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ExperimentUser record) {
        return experimentUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ExperimentUser add(ExperimentUser record) {
        experimentUserMapper.insert(record);
        return record;
    }

    @Override
    public List<Integer> getEPIDsByUserID(Integer userId) {
        List<ExperimentUser> epUsers = experimentUserMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(epUsers)) {
            return Lists.newArrayList();
        }
        return Lists.transform(epUsers, new Function<ExperimentUser, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable ExperimentUser experimentUser) {
                return experimentUser.getEpId();
            }
        });
    }

    @Override
    public int batchAdd(Integer epId, List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return 0;
        }
        return experimentUserMapper.batchInsert(epId, userIds);
    }

    @Override
    public List<Integer> getUserIdsByEpId(Integer epId) {
        if(epId < 0) {
            return null;
        }
        return experimentUserMapper.selectUserIdsByEpId(epId);
    }
}
