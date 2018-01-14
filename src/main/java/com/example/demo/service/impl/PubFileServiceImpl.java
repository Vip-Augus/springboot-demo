package com.example.demo.service.impl;

import com.example.demo.config.MinioConfigBean;
import com.example.demo.dao.PubFileMapper;
import com.example.demo.model.PubFile;
import com.example.demo.service.FileManageService;
import com.example.demo.service.PubFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公共服务实现类
 *
 * @author by JingQ on 2018/1/14
 */
@Slf4j
@Service
public class PubFileServiceImpl implements PubFileService{

    /**
     * minio配置
     */
    @Autowired
    private MinioConfigBean minioConfigBean;
    /**
     * 公共文件Service
     */
    @Autowired
    private PubFileMapper pubFileMapper;

    /**
     * 文件管理
     */
    @Autowired
    private FileManageService fileManageServiceImpl;


    @Override
    public int addPubFile(PubFile pubFile) {
        return pubFileMapper.insert(pubFile);
    }

    @Override
    public void deletePubFileById(Integer id) {
        PubFile file = pubFileMapper.selectByPrimaryKey(id);
        fileManageServiceImpl.deleteObject(StringUtils.replace(file.getFileUrl(), minioConfigBean.getIp(), ""));
        pubFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PubFile> getList() {
        return pubFileMapper.getAll();
    }
}
