package com.example.demo.base;

/**
 * 基础服务模板
 * Author JingQ on 2017/12/24.
 */
public interface BaseServiceTemplate<T> {

    T getById(Integer id);

    int update(T record);

    int deleteById(Integer id);

    int insert(T record);

}
