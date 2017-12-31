package com.example.demo.service.impl;

import com.example.demo.config.MinioConfigBean;
import com.example.demo.service.FileManageService;
import com.example.demo.util.file.UploadObject;
import io.minio.MinioClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件上传和下载服务
 * Author JingQ on 2017/12/25.
 */
@Service
public class FileManageServiceImpl implements FileManageService {

    private static Logger logger = LoggerFactory.getLogger(FileManageServiceImpl.class);

    private static MimetypesFileTypeMap mimetypesFileTypeMap;

    @Autowired
    private MinioConfigBean configBean;

    @Resource(name = "minioGenericObjectPool")
    private GenericObjectPool<MinioClient> genericObjectPool;

    @Override
    public String upload(UploadObject object) {
        StringBuilder url = new StringBuilder();
        MinioClient minioClient = null;
        try {
            minioClient = genericObjectPool.borrowObject();
            byte[] bytes = IOUtils.toByteArray(object.getIs());
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            String mimeType =  mimetypesFileTypeMap.getContentType(object.getFullName());
            logger.info(">>>>>>>>>>>>>>>>>>>正在上传到Minio");
            minioClient.putObject(configBean.getBucketName(), object.getDir() + object.getFullName(),bis, bytes.length, mimeType);
            url.append(configBean.getStaticUrl()).append(object.getDir()).append(object.getFullName());
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
            genericObjectPool.returnObject(minioClient);
        }
        return url.toString();
    }

    @Override
    public InputStream getInputStreamFromObject(UploadObject object) {
        InputStream is;
        MinioClient minioClient = null;
        try {
            minioClient = genericObjectPool.borrowObject();
            logger.info(">>>>>>>>>>>>>>>>>>>正在下载");
            minioClient.statObject(configBean.getBucketName(), object.getDir()+object.getFullName());
            is = minioClient.getObject(configBean.getBucketName(), object.getDir()+object.getFullName());
            logger.info(">>>>>>>>>>>>>>>>>>>下载成功" + object.getDir() + object.getFullName());
        } catch (Exception e) {
            logger.error("下载失败", e);
            return null;
        } finally {
            try {
                genericObjectPool.returnObject(minioClient);
            } catch (Exception e) {
                logger.error("归还失败");
            }
        }
        return is;
    }

    @Override
    public void deleteObject(String objectKey) {
        MinioClient client = null;
        try {
            client = genericObjectPool.borrowObject();
            client.removeObject(configBean.getBucketName(), objectKey);
        } catch (Exception e) {
            logger.error("Delete object failed. bucket="+configBean.getBucketName()+" key="+objectKey+", due to " + e.getMessage());
        } finally {
            genericObjectPool.returnObject(client);
        }
    }


    /**
     * 判断Bucket是否创建--每次判断会耗时,所以直接创建好,不校验了
     * @param client        minio
     * @param bucketName    bucketName
     * @throws Exception    异常
     */
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
