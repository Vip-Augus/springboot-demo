package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Experiment;
import com.example.demo.model.User;
import com.example.demo.model.dto.ExperimentDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ClassroomService;
import com.example.demo.service.ExperimentService;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.service.UserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.ImportUtil;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.convert.EPConverter;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * 实验课程（最外层）
 * Author by JingQ on 2018/1/2
 */
@Slf4j
@RestController
@RequestMapping("/web/ep")
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

    /**
     * 实验室服务
     */
    @Autowired
    private ClassroomService classroomServiceImpl;

    /**
     * 用户服务
     */
    @Autowired
    private UserService userServiceImpl;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON add(@RequestBody Experiment experiment, HttpServletRequest request) {
        SingleResult<Experiment> result = new SingleResult<>();
        //插入成功后，将老师id与实验课绑定
        Experiment insertEP = experimentServiceImpl.add(experiment);
        if (insertEP == null) {
            result.returnError(CodeConstants.CLASS_TIME_CONFLICT);
            return (JSON) JSON.toJSON(result);
        }
        List<String> tmp = Lists.newArrayList(insertEP.getTIds().split(SPLIT_SIGNAL));
        if (!CollectionUtils.isEmpty(tmp)) {
            List<Integer> tIDs = Lists.transform(tmp, new Function<String, Integer>() {
                @Nullable
                @Override
                public Integer apply(@Nullable String s) {
                    return Integer.parseInt(s);
                }
            });
            experimentUserServiceImpl.batchAdd(insertEP.getId(), tIDs);
        }
        log.info("实验课创建成功: ", insertEP.getId());
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
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        try {
            List<Integer> epIds = experimentUserServiceImpl.getEPIDsByUserID(user.getId());
            if (!CollectionUtils.isEmpty(epIds)) {
                List<Experiment> experimentList = experimentServiceImpl.getByIds(epIds);
                result.returnSuccess(epConverter.epList2DTOList(experimentList));
            }
            result.returnSuccess(Lists.newArrayList());
        } catch (Exception e) {
            log.error("查询实验课失败: ", e);
            result.returnError(e.getMessage());
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteEP(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            experimentUserServiceImpl.deleteById(id);
        } catch (Exception e) {
            log.error("删除实验课失败: ", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(id);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateEP(@RequestBody Experiment experiment, HttpServletRequest request) {
        SingleResult<Experiment> result = new SingleResult<>();
        try {
            experimentServiceImpl.update(experiment);
            log.info("实验室信息更新成功:", experiment);
            result.returnSuccess(experiment);
        } catch (Exception e) {
            log.error("实验室信息更新失败:", e);
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Experiment> result = new SingleResult<>();
        Experiment record = experimentServiceImpl.getById(id);
        result.returnSuccess(record);
        return (JSON) JSON.toJSON(result);
    }


    @RequestMapping(value = "importStudent", method = RequestMethod.POST)
    @ResponseBody
    public JSON importStudent(@RequestParam("file") MultipartFile file, @RequestParam("epId") Integer epId, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        List<String> fileUserIds = filterUserIds(file);
        List<User> existUsers = userServiceImpl.getByIdNumbers(fileUserIds, UserType.STUDENT.getCode());
        List<Integer> existUserIds = Lists.transform(existUsers, new Function<User, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable User user) {
                return user.getId();
            }
        });
        result.returnSuccess(experimentUserServiceImpl.batchAdd(epId, existUserIds));
        return (JSON) JSON.toJSON(result);
    }

    private List<String> filterUserIds(MultipartFile file) {
        List<String> contentList = Lists.newArrayList();
        try {
            if (file.isEmpty()) {
                return null;
            }
            contentList = ImportUtil.exportListFromExcel(file.getInputStream(), 0);
            Iterator<String> iterator = contentList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals("|null|null|null|null|null|null")) {
                    iterator.remove();
                }
            }
        } catch (Exception e) {
        }
        //去掉第一行
        contentList.remove(0);
        if (CollectionUtils.isEmpty(contentList)) {
            return null;
        }
        List<String> userIds = Lists.newArrayList();
        for (String tmp : contentList) {
            userIds.add(Lists.newArrayList(tmp.split("\\|")).get(1));
        }
        return userIds;
    }
}
