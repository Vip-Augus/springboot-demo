package com.example.demo.util.file;

import java.io.InputStream;

/**
 * 上传文件对象
 * Author JingQ on 2017/12/26.
 */
public class UploadObject {

    private InputStream is;//上传文件流

    private String fullName;//文件名包含后缀，作为oss上传key

    private String dir;//Minio的上传目录

    private String downloadName;

    public UploadObject() {
    }

    public UploadObject(String fullName, String dir) {
        this.fullName = fullName;
        this.dir = dir;
    }

    public UploadObject(InputStream is, String fullName, String dir) {
        this.is = is;
        this.fullName = fullName;
        this.dir = dir;
    }

    public UploadObject(InputStream is, String fullName, String dir, String downloadName) {
        this.is = is;
        this.fullName = fullName;
        this.dir = dir;
        this.downloadName = downloadName;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
}
