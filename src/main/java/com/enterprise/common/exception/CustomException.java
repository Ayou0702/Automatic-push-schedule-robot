package com.enterprise.common.exception;

import com.enterprise.entity.vo.ExceptionVo;

/**
 * 自定义异常
 *
 * @author PrefersMin
 * @version 1.2
 */
public class CustomException extends Exception  {

    /**
     * 异常对象实体类
     */
    private final ExceptionVo exceptionVo;

    /**
     * 自定义异常
     *
     * @author PrefersMin
     *
     * @param errorMessage 异常消息
     */
    public CustomException(String errorMessage) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
    }

    /**
     * 异常对象实体类
     *
     * @author PrefersMin
     *
     * @param errorMessage 异常消息
     * @param errorCode 异常代码
     */
    public CustomException(String errorMessage, int errorCode) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
        exceptionVo.setErrorCode(errorCode);
    }

    /**
     * 异常对象实体类
     *
     * @author PrefersMin
     *
     * @param errorMessage 异常消息
     * @param errorState 异常状态
     * @param errorCode 异常代码
     */
    public CustomException(String errorMessage, String errorState,int errorCode) {
        this.exceptionVo = new ExceptionVo();
        exceptionVo.setErrorMessage(errorMessage);
        exceptionVo.setErrorState(errorState);
        exceptionVo.setErrorCode(errorCode);
    }

}
