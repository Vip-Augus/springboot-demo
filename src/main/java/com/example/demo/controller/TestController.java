package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author JingQ on 2017/12/20.
 */
@RestController
@SpringBootApplication
public class TestController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        return "Hello World";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        User user = new User();
        user.setName("Augus");
        user.setAddress("12-114");
        user.setCollegeId(15);
        user.setType(0);
        user.setIdNumber("2014327101027");
        userServiceImpl.insert(user);
    }

}
