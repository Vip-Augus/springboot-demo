package com.example.demo.util.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author JingQ on 2018/1/11.
 */
public class ExceptionDefinition implements BusinessMessage, Serializable {

    private static final long serialVersionUID = -6234315192772871568L;

    private String code;

    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public ExceptionDefinition(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
