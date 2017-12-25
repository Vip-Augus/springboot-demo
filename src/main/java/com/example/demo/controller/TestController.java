package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void testUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "D://test/";
            File saveFile = new File(filePath + fileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(saveFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downFile(HttpServletRequest request, HttpServletResponse response) {
        String filePath = "D://test/";
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String fileName = "告白气球1.png";
        String target = "F://1.png";
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                throw new Exception("文件不存在");
            }
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
