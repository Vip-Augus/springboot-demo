package com.example.demo.util.result;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
