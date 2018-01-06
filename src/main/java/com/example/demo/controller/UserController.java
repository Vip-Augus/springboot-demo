package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.service.UserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.ImportUtil;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.convert.UserConverter;
import com.example.demo.util.result.SingleResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
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
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * 用户控制器
 * Author by JingQ on 2018/1/1
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ExperimentUserService experimentUserServiceImpl;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public JSON login(@RequestBody User userParam, HttpServletRequest request) {
        SingleResult<UserDTO> result = new SingleResult<>();
        User user = userServiceImpl.getByIdNumber(userParam.getIdNumber(), userParam.getType());
        //校验用户信息
        if (user == null || !userServiceImpl.checkPassword(userParam, user)) {
            result.returnError(CodeConstants.INVALID_USER_INFO);
            return (JSON) JSON.toJSON(result);
        }
        HttpSession session = request.getSession();
        //用户成功登录后，将数据存在session中，将密码清除掉
        UserDTO userDTO = userConverter.user2DTO(user);
        session.setAttribute(CodeConstants.USER_INFO_CONSTANT, user);
        result.returnSuccess(userDTO);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "registry", method = RequestMethod.POST)
    @ResponseBody
    public JSON registry(@RequestBody User userParam, HttpServletRequest request) {
        SingleResult<UserDTO> result = new SingleResult<>();
        User user = userServiceImpl.getByIdNumber(userParam.getIdNumber(), userParam.getType());
        if (user != null) {
            result.returnError(CodeConstants.USER_IS_EXISTS);
            return (JSON) JSON.toJSON(result);
        }
        try {
            userServiceImpl.add(userParam);
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(userConverter.user2DTO(userParam));
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public JSON getUser(HttpServletRequest request) {
        User user;
        SingleResult<User> result = new SingleResult<>();
        try {
            user = SessionUtil.getUser(request.getSession());
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(user);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "importStudent", method = RequestMethod.POST)
    @ResponseBody
    public JSON importStudent(@RequestParam("file") MultipartFile file, @RequestParam("epId") Integer epId, HttpServletRequest request) {
        User user;
        SingleResult<Integer> result = new SingleResult<>();
        try {
            user = SessionUtil.getUser(request.getSession());
            //不是老师,无法导入学生资料
            if (!UserType.TEACHER.equals(UserType.fromCode(user.getType()))) {
                result.returnError(CodeConstants.FAIL_CREATE_EP);
                return (JSON) JSON.toJSON(result);
            }
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
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