package com.example.demo.service.impl;

import com.example.demo.config.MinioConfigBean;
import com.example.demo.service.FileManage;
import com.example.demo.util.File.UploadObject;
import io.minio.MinioClient;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.ByteArrayInputStream;

/**
 * Author JingQ on 2017/12/25.
 */
@Service
public class FileManageImpl implements FileManage {

    private Logger logger = LoggerFactory.getLogger(FileManageImpl.class);

    private MinioClient minioClient;

    private static MimetypesFileTypeMap mimetypesFileTypeMap;

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

    @Override
    public String upload(UploadObject object) {
        StringBuilder url = new StringBuilder();
        minioClient = getMinioClient();
        try {
            checkBucketExists(minioClient, configBean.getBucketName());
            byte[] bytes = IOUtils.toByteArray(object.getIs());
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            String mimeType =  mimetypesFileTypeMap.getContentType(object.getFullName());
            logger.info(">>>>>>>>>>>>>>>>>>>正在上传到Minio");
            minioClient.putObject(configBean.getBucketName(), object.getDir() + object.getFullName(),bis, bytes.length, mimeType);
            url.append(configBean.getIp()).append("/").append(configBean.getBucketName()).append("/").append(object.getDir()).append(object.getFullName());
            bis.close();
            logger.info(">>>>>>>>>>>>>>>>>>>upload success>>>>" + url);
        } catch (Exception e) {
            logger.error("上传失败", e);
        } finally {
            try {
                if (object.getIs() != null) {
                    object.getIs().close();
                }
            } catch (Exception e) {
                logger.error("输入流关闭失败", e);
            }
        }
        return url.toString();
    }


    private void checkBucketExists(MinioClient client, String bucketName) throws Exception {
        boolean result = client.bucketExists(bucketName);
        if (!result) {
            client.makeBucket(bucketName);
        }
    }

    static {
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("application/javascript js");
        mimetypesFileTypeMap.addMimeTypes("application/msword doc docx docm");
        mimetypesFileTypeMap.addMimeTypes("application/pdf pdf");
        mimetypesFileTypeMap.addMimeTypes("application/postscript ps");
        mimetypesFileTypeMap.addMimeTypes("application/rss+xml rss");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-excel xls xlsx xlsm");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-powerpoint ppt pptx pptm");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.oasis.database odb");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.oasis.presentation odp");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.oasis.spreadsheet ods");
        mimetypesFileTypeMap.addMimeTypes("application/vnd.oasis.text odt");
        mimetypesFileTypeMap.addMimeTypes("application/x-awk awk");
        mimetypesFileTypeMap.addMimeTypes("application/x-blender blend");
        mimetypesFileTypeMap.addMimeTypes("application/x-cd-image iso");
        mimetypesFileTypeMap.addMimeTypes("application/x-compress zip gz tar rar");
        mimetypesFileTypeMap.addMimeTypes("application/x-deb deb");
        mimetypesFileTypeMap.addMimeTypes("application/x-font-otf otf OTF");
        mimetypesFileTypeMap.addMimeTypes("application/x-font-ttf ttf TTF");
        mimetypesFileTypeMap.addMimeTypes("application/x-java-applet class");
        mimetypesFileTypeMap.addMimeTypes("application/x-java-archive jar");
        mimetypesFileTypeMap.addMimeTypes("application/x-ms-dos-executable exe msi");
        mimetypesFileTypeMap.addMimeTypes("application/x-perl pl");
        mimetypesFileTypeMap.addMimeTypes("application/x-php php");
        mimetypesFileTypeMap.addMimeTypes("application/x-rpm rpm");
        mimetypesFileTypeMap.addMimeTypes("application/x-sharedlib o");
        mimetypesFileTypeMap.addMimeTypes("application/x-shellscript sh");
        mimetypesFileTypeMap.addMimeTypes("application/x-trash autosave");
        mimetypesFileTypeMap.addMimeTypes("application/xml xml");
        mimetypesFileTypeMap.addMimeTypes("audio/ac3 ac3");
        mimetypesFileTypeMap.addMimeTypes("audio/midi mid");
        mimetypesFileTypeMap.addMimeTypes("audio/x-generic wav wma mp3 ogg");
        mimetypesFileTypeMap.addMimeTypes("image/x-generic bmp jpg jpeg png tif tiff xpm wmf emf");
        mimetypesFileTypeMap.addMimeTypes("image/x-eps eps");
        mimetypesFileTypeMap.addMimeTypes("image/svg+xml svg svgz");
        mimetypesFileTypeMap.addMimeTypes("text/css css");
        mimetypesFileTypeMap.addMimeTypes("text/html htm html");
        mimetypesFileTypeMap.addMimeTypes("text/plain txt");
        mimetypesFileTypeMap.addMimeTypes("text/rtf rtf");
        mimetypesFileTypeMap.addMimeTypes("text/x-bibtex bib");
        mimetypesFileTypeMap.addMimeTypes("text/x-c++hdr h");
        mimetypesFileTypeMap.addMimeTypes("text/x-c++src cpp c++");
        mimetypesFileTypeMap.addMimeTypes("text/x-csrc c");
        mimetypesFileTypeMap.addMimeTypes("text/x-java java");
        mimetypesFileTypeMap.addMimeTypes("text/x-log log");
        mimetypesFileTypeMap.addMimeTypes("text/x-pascal pas");
        mimetypesFileTypeMap.addMimeTypes("text/x-po po pot");
        mimetypesFileTypeMap.addMimeTypes("text/x-python py");
        mimetypesFileTypeMap.addMimeTypes("text/x-sql sql");
        mimetypesFileTypeMap.addMimeTypes("text/x-tcl tcl");
        mimetypesFileTypeMap.addMimeTypes("text/x-tex tex");
        mimetypesFileTypeMap.addMimeTypes("text/xml xml osm");
        mimetypesFileTypeMap.addMimeTypes("video/x-generic wmv mpeg mp4 ogv swf mov dvd osp");
    }

}
