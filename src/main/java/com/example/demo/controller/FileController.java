package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.MinioConfigBean;
import com.example.demo.model.PubFile;
import com.example.demo.model.User;
import com.example.demo.model.dto.PubFileDTO;
import com.example.demo.service.FileManageService;
import com.example.demo.service.PubFileService;
import com.example.demo.service.UserService;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.convert.PubFileConverter;
import com.example.demo.util.file.UploadObject;
import com.example.demo.util.result.BusinessException;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;

/**
 * Author JingQ on 2017/12/20.
 */
@RestController
@RequestMapping(value = "/web/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    /**
     * 公共文件存放的文件夹
     */
    private static final String BASE_PATH = "pub/";

    @Autowired
    private FileManageService fileManageService;

    @Autowired
    private PubFileService pubFileServiceImpl;

    @Autowired
    private PubFileConverter fileConverter;

    /**
     * 公共文件上传
     * @param file      文件
     * @param request   请求
     * @return          上传结果,正常结果为1
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSON upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        User user;
        try {
            user = SessionUtil.getUser(request.getSession());
        } catch (Exception e) {
            LOGGER.error("用户未登录", e);
            result.returnError("用户未登录");
            return (JSON) JSON.toJSON(result);
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(file.getInputStream());
                UploadObject object = new UploadObject(bis, fileName, BASE_PATH);
                String url = fileManageService.upload(object);
                pubFileServiceImpl.addPubFile(fileConverter.dto2DO(fillPubFile(fileName, url, user.getIdNumber(), user.getId())));
            } catch (Exception ex) {
                LOGGER.error("", ex);
                result.returnError("上传失败!");
                return (JSON) JSON.toJSON(result);
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
        result.returnSuccess(1);
        return (JSON) JSON.toJSON(result);
    }

    /**
     * 获取全部公共文件
     *
     * @param request   请求
     * @return          全部公共文件
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getAll(HttpServletRequest request) {
        ListResult<PubFileDTO> result = new ListResult<>();
        try {
            List<PubFileDTO> pubFileDTOS = fileConverter.files2DTOs(pubFileServiceImpl.getList());
            result.returnSuccess(pubFileDTOS);
        } catch (BusinessException e) {
            LOGGER.error("获取列表失败", e);
            result.returnError(e);
            return (JSON) JSON.toJSON(result);
        } catch (Exception e) {
            LOGGER.error("获取列表失败", e);
            result.returnError("获取列表失败");
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
    }

    /**
     * 删除某个文件--根据主键ID
     * @param id           公共文件主键ID
     * @param request       请求
     * @return              删除成功为1
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            pubFileServiceImpl.deletePubFileById(id);
            result.returnSuccess(1);
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            result.returnError("删除失败");
            return (JSON) JSON.toJSON(result);
        }
        return (JSON) JSON.toJSON(result);
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

    private PubFileDTO fillPubFile(String fileName, String url, String idNumber, Integer userId) {
        PubFileDTO pubFile = new PubFileDTO();
        pubFile.setName(fileName);
        pubFile.setUploadDate(new Date());
        pubFile.setIdNumber(idNumber);
        pubFile.setCreateId(userId);
        pubFile.setFileUrl(url);
        //先设一个默认值
        pubFile.setIsDirectory(0);
        return pubFile;
    }
}
