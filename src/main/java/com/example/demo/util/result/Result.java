package com.example.demo.util.result;

import com.example.demo.model.enums.ResultType;
import org.apache.ibatis.executor.ErrorContext;

import java.io.Serializable;

/**
 * Author by JingQ on 2018/1/1
 */
public abstract class Result implements Serializable{

    private static final long serialVersionUID = -3295991523122453977L;

    private boolean success = false;

    private ErrorContext errorContext;

    private String errorMessage;

    private Integer code;

    private String description;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        this.code = ResultType.SUCCESS.getCode();
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
