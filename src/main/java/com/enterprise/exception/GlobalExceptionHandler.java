package com.enterprise.exception;

import com.enterprise.util.LogUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestControllerAdvice

public class GlobalExceptionHandler {

    /**
     * 捕捉运行时异常
     *
     * @author PrefersMin
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = RuntimeException.class)
    public void handler(RuntimeException e) {
        LogUtil.error("运行时异常:----------------{}\n"+ e.getMessage());
    }

    /**
     * 捕捉空指针异常
     *
     * @author PrefersMin
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = NullPointerException.class)
    public void handler(NullPointerException e) {
        LogUtil.error("空指针异常:----------------{}\n"+ e.getMessage());
    }

}
