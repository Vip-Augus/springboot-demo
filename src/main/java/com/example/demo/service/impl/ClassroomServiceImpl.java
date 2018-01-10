package com.example.demo.service.impl;

import com.example.demo.dao.ClassroomMapper;
import com.example.demo.model.Classroom;
import com.example.demo.model.Experiment;
import com.example.demo.model.dto.ClassroomQueryParam;
import com.example.demo.model.enums.ClassTime;
import com.example.demo.model.enums.ClassroomStatus;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.ExperimentService;
import com.example.demo.util.PeriodUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Author by JingQ on 2018/1/4
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private ExperimentService experimentServiceImpl;

    @Override
    public Classroom getById(Integer id) {
        return classroomMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Classroom record) {
        return classroomMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return classroomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Classroom add(Classroom record) {
        classroomMapper.insert(record);
        return record;
    }

    @Override
    public List<Classroom> getList(ClassroomQueryParam queryParam) {
        Calendar calendar = Calendar.getInstance();
        //当前是周几
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        SimpleDateFormat sdf1 = PeriodUtil.getSimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2 = PeriodUtil.getSimpleDateFormat("yyyy-MM-dd");
        String classTimeFormat = sdf1.format(calendar.getTime());
        String currentPeriod = sdf2.format(calendar.getTime());
        //现在上的是第几节课
        ClassTime classTime = ClassTime.fromTime(classTimeFormat);
        List<Classroom> classrooms = classroomMapper.query(queryParam);
        for (Classroom classroom : classrooms) {
            if (ClassroomStatus.STOP.getCode() == classroom.getStatus()) {
                //如果实验室状态为停用,跳过判断当天使用情况
                continue;
            }
            if (ClassTime.OTHER == classTime) {
                classroom.setStatus(ClassroomStatus.FREE.getCode());
                continue;
            }
            if (getPeriods(classTime, currentPeriod, classroom).contains(classTime.getCode())) {
                classroom.setStatus(ClassroomStatus.TAKE_UP.getCode());
            } else {
                classroom.setStatus(ClassroomStatus.FREE.getCode());
            }
        }
        return classrooms;
    }

    @Override
    public List<Experiment> getUsingStatement(Integer cid, String currentTime) {
        return experimentServiceImpl.getUsingStatementByCID(cid, null, currentTime);
    }

    @Override
    public List<Integer> getPeriods(ClassTime classTime, String currentPeriod, Classroom classroom) {
        if (classTime == null) {
            return Lists.newArrayList();
        }
        //某一天该实验室所上的实验课
        List<Experiment> experiments = experimentServiceImpl.getUsingStatementByCID(classroom.getId(), classTime.getCode(), currentPeriod);
        List<Integer> usingTimes = Lists.newArrayList();
        for (Experiment experiment : experiments) {
            usingTimes.addAll(PeriodUtil.getSectionClass(experiment.getClassBegin(), experiment.getClassEnd()));
        }
        return usingTimes;
    }
}
