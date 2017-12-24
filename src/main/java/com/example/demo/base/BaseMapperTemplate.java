package com.example.demo.base;

/**
 * 基础的增删查改
 * Author JingQ on 2017/12/24.
 */
public interface BaseMapperTemplate<T> {

    T selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int updateByPrimaryKey(T record);

}
