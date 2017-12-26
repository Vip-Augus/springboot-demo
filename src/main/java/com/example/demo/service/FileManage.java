package com.example.demo.service;

import com.example.demo.util.File.UploadObject;
import io.minio.MinioClient;

/**
 * Author JingQ on 2017/12/25.
 */
public interface FileManage {
    MinioClient getMinioClient();

    String upload(UploadObject object);

}
