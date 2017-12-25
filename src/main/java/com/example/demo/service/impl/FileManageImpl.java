package com.example.demo.service.impl;

import com.example.demo.config.MinioConfigBean;
import com.example.demo.service.FileManage;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author JingQ on 2017/12/25.
 */
@Service
public class FileManageImpl implements FileManage {

    private MinioClient minioClient;

    @Autowired
    private MinioConfigBean configBean;

    @Override
    public MinioClient getMinioClient() {
        try {
            if (minioClient == null) {
                synchronized (FileManageImpl.class) {
                    if (minioClient == null) {
                        minioClient = new MinioClient(configBean.getIp(), configBean.getAccessKey(), configBean.getSecretKey());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minioClient;
    }


}
