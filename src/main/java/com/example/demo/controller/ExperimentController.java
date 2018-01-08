package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Experiment;
import com.example.demo.model.User;
import com.example.demo.model.dto.ExperimentDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ExperimentService;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.convert.EPConverter;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 实验课程（最外层）
 * Author by JingQ on 2018/1/2
 */
@RestController
@RequestMapping("/ep")
public class ExperimentController {

    private static final String SPLIT_SIGNAL = ",";

    /**
     * 课程信息服务
     */
    @Autowired
    private ExperimentService experimentServiceImpl;

    /**
     * ep转换器
     */
    @Autowired
    private EPConverter epConverter;

    /**
     * 实验课程和用户映射服务
     */
    @Autowired
    private ExperimentUserService experimentUserServiceImpl;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON add(@RequestBody Experiment experiment, HttpServletRequest request) {
        SingleResult<Experiment> result = new SingleResult<>();
        User user = null;
        try {
            user = SessionUtil.getUser(request.getSession());
            if (!UserType.TEACHER.equals(UserType.fromCode(user.getType()))) {
                result.returnError(CodeConstants.FAIL_CREATE_EP);
                return (JSON) JSON.toJSON(result);
            }
        } catch (Exception e) {
            result.returnError(e.getMessage());
        }
        //插入成功后，将老师id与实验课绑定
        Experiment insertEP = experimentServiceImpl.add(experiment);
        List<String> tmp = Lists.newArrayList(insertEP.gettIds().split(SPLIT_SIGNAL));
        if (!CollectionUtils.isEmpty(tmp)) {
            List<Integer> tIDs  = Lists.transform(tmp, new Function<String, Integer>() {
                @Nullable
                @Override
                public Integer apply(@Nullable String s) {
                    return Integer.parseInt(s);
                }
            });
            experimentUserServiceImpl.batchAdd(insertEP.getId(), tIDs);
        }
        result.returnSuccess(experiment);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON elective(HttpServletRequest request) {
        ListResult<ExperimentDTO> result = new ListResult<>();
        User user;
        try {
            user = SessionUtil.getUser(request.getSession());
        }catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        List<Integer> epIds = experimentUserServiceImpl.getEPIDsByUserID(user.getId());
        List<Experiment> experimentList = experimentServiceImpl.getByIds(epIds);
        result.returnSuccess(epConverter.epList2DTOList(experimentList));
        return (JSON) JSON.toJSON(result);
    }
}
