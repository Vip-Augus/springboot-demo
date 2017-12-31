package com.example.demo.controller;

import com.example.demo.config.MinioConfigBean;
import com.example.demo.service.FileManageService;
import com.example.demo.service.UserService;
import com.example.demo.util.file.UploadObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Author JingQ on 2017/12/20.
 */
@RestController
@RequestMapping(value = "/file")
@SpringBootApplication
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private static final String TEST_DIR = "test/";

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private FileManageService fileManageService;

    @Autowired
    private MinioConfigBean minioConfigBean;


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
                UploadObject object = new UploadObject(bis, fileName, TEST_DIR);
                fileManageService.upload(object);
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

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void downFile(@RequestBody UploadObject uploadObject, HttpServletRequest request, HttpServletResponse response) {
        InputStream is = fileManageService.getInputStreamFromObject(uploadObject);
        if (is == null) {
            response.setHeader("code", "NULL000000");
            return;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uploadObject.getFullName(), "utf-8"));
            bos = new BufferedOutputStream(response.getOutputStream());
            bis = new BufferedInputStream(is);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
                bos.flush();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            try {
                is.close();
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
