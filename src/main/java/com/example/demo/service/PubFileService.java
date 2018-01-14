package com.example.demo.service;

import com.example.demo.model.PubFile;

import java.util.List;

/**
 * 公共文件服务
 *
 * @author by JingQ on 2018/1/14
 */
public interface PubFileService {

    /**
     * 添加
     *
     * @param pubFile 公共文件
     * @return 插入数量
     */
    int addPubFile(PubFile pubFile);

    /**
     * 删除
     *
     * @param id 主键ID
     */
    void deletePubFileById(Integer id);

    /**
     * 获取全部公共文件
     *
     * @return 全部公共文件
     */
    List<PubFile> getList();
}
