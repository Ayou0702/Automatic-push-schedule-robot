package com.enterprise.exception;

import com.enterprise.entity.vo.ExceptionVo;
import javax.annotation.Resource;

public class CustomException extends Exception  {

    @Resource
    ExceptionVo exceptionVo;

    public CustomException(String errorMessage) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
    }

    public CustomException(String errorMessage, int errorCode) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
        exceptionVo.setErrorCode(errorCode);
    }

    public CustomException(String errorMessage, String errorState,int errorCode) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
        exceptionVo.setErrorState(errorState);
        exceptionVo.setErrorCode(errorCode);
    }

    public CustomException(String errorMessage, String errorState, int errorCode, boolean errorCondition) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
        exceptionVo.setErrorState(errorState);
        exceptionVo.setErrorCode(errorCode);
        exceptionVo.setErrorCondition(errorCondition);
    }

    public ExceptionVo getExceptionVo() {
        return exceptionVo;
    }

    public void setExceptionVo(ExceptionVo exceptionVo) {
        this.exceptionVo = exceptionVo;
    }

}
