package com.example.demo.dao;

import com.example.demo.model.PubFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公共文件
 *
 * @author JingQ
 */
@Mapper
public interface PubFileMapper {
    /**
     * 通过id进行删除
     *
     * @param id 主键ID
     * @return 删除数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     *
     * @param record 公共文件对象
     * @return 插入数量
     */
    int insert(PubFile record);

    /**
     * 选择性插入
     *
     * @param record 公共文件对象
     * @return 插入数量
     */
    int insertSelective(PubFile record);

    /**
     * 根据主键ID查询
     *
     * @param id 主键ID
     * @return 公共文件
     */
    PubFile selectByPrimaryKey(Integer id);

    /**
     * 选择性更新
     *
     * @param record 公共文件
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(PubFile record);

    /**
     * 更新文件
     *
     * @param record 公共文件
     * @return 更新数量
     */
    int updateByPrimaryKey(PubFile record);

    /**
     * 获取全部---第一版
     *
     * @return 全部公共文件
     */
    List<PubFile> getAll();
}