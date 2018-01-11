package com.example.demo.util.result;

import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author JingQ on 2018/1/11.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5433056591268198786L;
    private Map<String, String> errorMessages = new HashMap();
    private BusinessMessage businessMessage = new ExceptionDefinition("11111111", "网络异常，请联系管理员");
    private String detailMessage;

    public BusinessException() {
    }

    public BusinessException(BusinessMessage businessMessage) {
        super(businessMessage.getMessage());
        this.businessMessage = businessMessage;
        this.detailMessage = businessMessage.getMessage();
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(Throwable cause, BusinessMessage businessMessage) {
        super(businessMessage.getMessage(), cause);
        this.businessMessage = businessMessage;
        this.detailMessage = cause.getMessage();
    }

    public BusinessException(BusinessMessage businessMessage, Throwable cause) {
        super(businessMessage.getMessage(), cause);
        this.businessMessage = businessMessage;
        this.detailMessage = cause.getMessage();
    }

    public BusinessException(BusinessMessage businessMessage, String detailMessage) {
        super(businessMessage.getMessage());
        this.businessMessage = businessMessage;
        this.detailMessage = detailMessage;
    }

    public BusinessException(BusinessMessage businessMessage, String detailMessage, Throwable cause) {
        super(businessMessage.getMessage(), cause);
        this.businessMessage = businessMessage;
        this.detailMessage = detailMessage;
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, BusinessMessage businessMessage) {
        super(businessMessage.getMessage(), cause, enableSuppression, writableStackTrace);
        this.businessMessage = businessMessage;
        this.detailMessage = cause.getMessage();
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(String message) {
        super(message);
        this.setBusinessMessage(new ExceptionDefinition("", message));
        this.detailMessage = message;
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(Throwable cause) {
        super(cause);
        this.setBusinessMessage(new ExceptionDefinition("", cause.getMessage()));
        this.detailMessage = cause.getMessage();
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.setBusinessMessage(new ExceptionDefinition("", message));
        this.detailMessage = cause.getMessage();
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(String code, String message) {
        super(message);
        this.setBusinessMessage(new ExceptionDefinition(code, message));
        this.detailMessage = message;
    }

    /** @deprecated */
    @Deprecated
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setBusinessMessage(new ExceptionDefinition(code, message));
        this.detailMessage = cause.getMessage();
    }

    public Map<String, String> getErrorMessages() {
        return this.errorMessages;
    }

    public static long getSerialversionuid() {
        return -5433056591268198786L;
    }

    public void setErrorMessages(Set<? extends ConstraintViolation<? extends Object>> violations) {
        Iterator i$ = violations.iterator();

        while(i$.hasNext()) {
            ConstraintViolation<? extends Object> violation = (ConstraintViolation)i$.next();
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            this.errorMessages.put(propertyPath, message);
        }

    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String fieldName, String message) {
        this.errorMessages.put(fieldName, message);
    }

    public void addBindingResultTo(BindingResult result) {
        Iterator i$ = this.errorMessages.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)i$.next();
            result.rejectValue((String)entry.getKey(), "", (String)entry.getValue());
        }

    }

    public String getCode() {
        return this.businessMessage.getCode();
    }

    /** @deprecated */
    @Deprecated
    public void setCode(String code) {
        if(this.businessMessage != null && this.businessMessage instanceof ExceptionDefinition) {
            ((ExceptionDefinition)this.businessMessage).setCode(code);
        }

    }

    public String getMessage() {
        return this.businessMessage != null?this.businessMessage.getMessage():null;
    }

    public BusinessMessage getBusinessMessage() {
        return this.businessMessage;
    }

    public void setBusinessMessage(ExceptionDefinition businessMessage) {
        if(businessMessage != null) {
            this.businessMessage = businessMessage;
        }

    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
