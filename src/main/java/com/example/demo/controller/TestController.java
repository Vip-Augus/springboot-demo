package com.example.demo.controller;

import com.example.demo.service.FileManage;
import com.example.demo.service.UserService;
import com.example.demo.util.File.UploadObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Author JingQ on 2017/12/20.
 */
@RestController
@SpringBootApplication
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private FileManage fileManage;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        return "Hello World";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void testUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(file.getInputStream());
                UploadObject object = new UploadObject(bis, fileName, "test/");
                fileManage.upload(object);
            } catch (Exception ex) {
                LOGGER.error("", ex);
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downFile(HttpServletRequest request, HttpServletResponse response) {

    }

}
