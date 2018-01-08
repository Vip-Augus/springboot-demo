package com.example.demo.util.result;

import org.apache.ibatis.executor.ErrorContext;

import java.util.List;

/**
 * 批量结果返回类
 * Author by JingQ on 2018/1/1
 */
public class ListResult<T> extends Result {

    private List<T> list;

    public ListResult() {

    }

    public void returnSuccess(List<T> list) {
        setSuccess(true);
        this.list = list;
    }

    public void returnError(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    public void returnError(String errorMessage, ErrorContext errorContext) {
        setErrorMessage(errorMessage);
        setErrorContext(errorContext);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
