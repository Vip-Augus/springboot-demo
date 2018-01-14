package com.example.demo.exception;

import com.example.demo.util.CodeConstants;
import com.example.demo.util.result.BusinessException;

/**
 * 常用异常定义
 *
 * @author by JingQ on 2018/1/13
 */
public class ExceptionDefinitions {

    /**
     * 重新登录
     */
    public static final BusinessException LOGIN_AGAIN = new BusinessException("11111111", CodeConstants.LOGIN_AGAIN);

    /**
     * 实验室已存在
     */
    public static final BusinessException CLASSROOM_CONFLICT = new BusinessException("CL000001", "实验室已存在");
}
