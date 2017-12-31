package com.example.demo.service;

import com.example.demo.util.file.UploadObject;

import java.io.InputStream;

/**
 * Author JingQ on 2017/12/25.
 */
public interface FileManageService {

    /**
     * 上传文件
     * @param object    文件对象
     * @return          上传路径
     */
    String upload(UploadObject object);

    /**
     * 下载文件
     * @param object    文件对象
     * @return          文件输入流
     */
    InputStream getInputStreamFromObject(UploadObject object);

    /**
     * 删除文件
     * @param objectKey bucketName+"/"+路径+全名
     */
    void deleteObject(String objectKey);

}
