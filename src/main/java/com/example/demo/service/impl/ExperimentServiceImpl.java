package com.example.demo.service.impl;

import com.example.demo.dao.ExperimentMapper;
import com.example.demo.model.Classroom;
import com.example.demo.model.Course;
import com.example.demo.model.Experiment;
import com.example.demo.model.enums.ClassTime;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.CourseService;
import com.example.demo.service.ExperimentService;
import com.example.demo.util.PeriodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author JingQ on 2017/12/24.
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired
    private ExperimentMapper experimentMapper;

    @Autowired
    private ClassroomService classroomServiceImpl;

    @Autowired
    private CourseService courseServiceImpl;

    @Override
    public Experiment getById(Integer id) {
        return experimentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Experiment record) {
        if (!isValid(record)) {
            return 0;
        }
        return experimentMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteById(Integer id) {
        return experimentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Experiment add(Experiment record) {
        Course course = courseServiceImpl.getById(record.getCourseId());
        //没有这门课---异常抛出后续再加
        if (course == null) {
            return null;
        }
        if (!isValid(record)) {
            return null;
        }
        experimentMapper.insert(record);
        return record;
    }

    @Override
    public List<Experiment> getByIds(List<Integer> ids) {
        return experimentMapper.selectByIds(ids);
    }

    @Override
    public List<Experiment> getUsingStatementByCID(Integer cid, Integer day, String currentPeriod) {
        return experimentMapper.selectInUseByClassroomId(cid, day, currentPeriod);
    }

    /**
     * 校验更新和插入时该实验室是否可用
     * @param record            实验课信息
     * @return                  实验室是否可用
     */
    private boolean isValid(Experiment record) {
        ClassTime classTime = ClassTime.fromCode(record.getDay());
        Classroom classroom = classroomServiceImpl.getById(record.getClassroomId());
        List<Integer> periodsBegin = classroomServiceImpl.getPeriods(classTime, record.getBeginPeriod(), classroom);
        List<Integer> periodsEnd = classroomServiceImpl.getPeriods(classTime, record.getEndPeriod(), classroom);
        //实验室地点被占用了,无法添加该课程
        if (periodsBegin.contains(record.getClassBegin()) || periodsBegin.contains(record.getClassEnd())
                || periodsEnd.contains(record.getClassBegin()) || periodsEnd.contains(record.getClassEnd())) {
            return false;
        }
        return true;
    }
}
