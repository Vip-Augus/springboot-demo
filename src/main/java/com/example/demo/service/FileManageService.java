package com.example.demo.service;

import com.example.demo.util.File.UploadObject;

import java.io.InputStream;

/**
 * Author JingQ on 2017/12/25.
 */
public interface FileManageService {

    String upload(UploadObject object);

    InputStream getInputStreamFromObject(UploadObject object);

}
