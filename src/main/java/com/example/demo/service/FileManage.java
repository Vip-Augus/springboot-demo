package com.example.demo.service;

import io.minio.MinioClient;

/**
 * Author JingQ on 2017/12/25.
 */
public interface FileManage {
    MinioClient getMinioClient();
}
