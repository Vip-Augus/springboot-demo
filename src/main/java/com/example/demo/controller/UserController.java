package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.UserPageDTO;
import com.example.demo.model.enums.UserType;
import com.example.demo.service.ExperimentUserService;
import com.example.demo.service.UserService;
import com.example.demo.util.CodeConstants;
import com.example.demo.util.ImportUtil;
import com.example.demo.util.MD5Util;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.convert.UserConverter;
import com.example.demo.util.result.SingleResult;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 用户控制器
 * Author by JingQ on 2018/1/1
 */
@Slf4j
@RestController
@RequestMapping(value = "/web/user")
public class UserController {

    private static final int PAGE_SIZE = 10;
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
            log.error(CodeConstants.INVALID_USER_INFO, userParam.getIdNumber());
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



    //获取教师和管理员信息
    @RequestMapping(value = "teacher", method = RequestMethod.GET)
    @ResponseBody
    public JSON getUserByType(HttpServletRequest request) {
        List<UserDTO> userList = Lists.newArrayList();
        SingleResult<List<UserDTO>> result = new SingleResult<>();
        try {
            int page = StringUtil.getInteger(request.getParameter("page"));
            String name = request.getParameter("name");
            userList = userConverter.users2DTOS(userServiceImpl.getTeachers(page * PAGE_SIZE, PAGE_SIZE, name));
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(userList);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getUsers(@RequestParam("type") Integer type, @RequestParam("page") Integer page) {
        SingleResult<UserPageDTO> result = new SingleResult<>();
        try {
            //前端大老说前端进行分页
            result.returnSuccess(userConverter.userPage2DTO(userServiceImpl.getByPage(type, 0, 0)));
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    //根据idNumber删除User
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteByIdNumber(@RequestParam("idNumber") String idNumber, HttpServletRequest request) {
        SingleResult<List<User>> result = new SingleResult<>();
        try {
            Boolean deleteBoolean = userServiceImpl.deleteByIdNumber(idNumber);
            if (!deleteBoolean) {
                result.returnError(CodeConstants.NO_DATA);
                return (JSON) JSON.toJSON(result);
            }
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(null);
        return (JSON) JSON.toJSON(result);
    }

    //根据idNumber更新UserAuthority
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    @ResponseBody
    public JSON updateUserAuth(@RequestParam("userId") Integer userId, @RequestParam("auth") String auth, HttpServletRequest request) {
        SingleResult<List<User>> result = new SingleResult<>();
        try {
            Integer authority = Integer.valueOf(auth, 2);
            Integer type = 1;
//            if ((authority & 1) == 1) {
//                type = 0;
//            }
            Boolean updateBoolean = userServiceImpl.updateUserAuth(userId, Integer.toHexString(authority), type);
            if (!updateBoolean) {
                result.returnError("更新失败");
                return (JSON) JSON.toJSON(result);
            }
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(null);
        return (JSON) JSON.toJSON(result);
    }

    //密码确认
    @RequestMapping(value = "password", method = RequestMethod.POST)
    @ResponseBody
    public JSON passwordConfirm(@RequestBody User userParam, HttpServletRequest request) {
        SingleResult<UserDTO> result = new SingleResult<>();
        User user = userServiceImpl.getByIdNumber(userParam.getIdNumber(), userParam.getType());
        //校验用户信息
        if (user == null || !userServiceImpl.checkPassword(userParam, user)) {
            result.returnError(CodeConstants.INVALID_USER_INFO);
            return (JSON) JSON.toJSON(result);
        }
        result.returnSuccess(null);
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "importUser", method = RequestMethod.POST)
    @ResponseBody
    public JSON importStudent(@RequestParam("userType") Integer userType, @RequestParam("file") MultipartFile file) {
        SingleResult<Integer> result = new SingleResult<>();
        List<User> users = filterUser(userType, file);
        try {
            result.returnSuccess(userServiceImpl.importUser(users));
        } catch (Exception e) {
            result.returnError(e.getMessage());
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    private List<User> filterUser(Integer userType, MultipartFile file) {
        List<String> contentList = Lists.newArrayList();
        try {
            if (file.isEmpty()) {
                return null;
            }
            contentList = ImportUtil.exportListFromExcel(file.getInputStream(), 0);
            Iterator<String> iterator = contentList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals("|null|null|null|null|null|null|null|null|null|null|null|null|null|null")) {
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
        String initPassword = "123456";
        List<User> users = Lists.newArrayList();
        for (String tmp : contentList) {
            List<String> content = Lists.newArrayList(tmp.split("\\|"));
            //学号,专业,姓名,班级,入学时间,籍贯,民族,出生日期,性别,政治面貌,手机号码,邮箱,家庭住址,简介
            User user = new User();
            user.setType(userType);
            user.setIdNumber(content.get(1));
            String salt = UUID.randomUUID().toString();
            String password = MD5Util.genMD5(MD5Util.genMD5(initPassword) + salt);
            user.setSalt(salt);
            user.setPassword(password);
            user.setProfession(content.get(2));
            user.setName(content.get(3));
            user.setClassTitle(content.get(4));
            user.setJoinTime(content.get(5));
            user.setNativePlace(content.get(6));
            user.setEthnic(content.get(7));
            user.setBirthday(content.get(8));
            user.setGender(Integer.parseInt(content.get(9)));
            user.setPolitical(content.get(10));
            user.setPhone(content.get(11));
            user.setEmail(content.get(12));
            user.setAddress(content.get(13));
            user.setIntroduction(content.get(14));
            users.add(user);
        }
        return users;
    }
}
