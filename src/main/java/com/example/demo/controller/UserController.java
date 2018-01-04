package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.convert.UserConverter;
import com.example.demo.util.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

}
