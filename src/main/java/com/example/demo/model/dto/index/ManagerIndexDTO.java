package com.example.demo.model.dto.index;

import com.example.demo.model.CourseReview;
import lombok.Data;

import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/13
 */
@Data
public class ManagerIndexDTO extends TeacherIndexDTO{
    protected List<CourseReview> courseReviewList;
}
