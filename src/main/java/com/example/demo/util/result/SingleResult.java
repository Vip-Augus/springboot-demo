package com.example.demo.util.result;

import org.apache.ibatis.executor.ErrorContext;

import java.io.Serializable;

/**
 * 单一结果返回类
 * Author by JingQ on 2018/1/1
 */
public class SingleResult<T> extends Result implements Serializable {

    private T entity = null;

    public SingleResult() {

    }

    public SingleResult(T entity) {
        this.entity = entity;
    }

    public void returnSuccess(T entity) {
        setSuccess(true);
        this.entity = entity;
    }

    public void returnError(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    public void returnError(String errorMessage, ErrorContext errorContext) {
        setErrorMessage(errorMessage);
        setErrorContext(errorContext);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
