package com.enterprise.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 *
 * @author Iwlthxcl
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler {

    /**
     * 捕捉运行时异常
     *
     * @author Iwlthxcl
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = RuntimeException.class)
    public void handler (RuntimeException e) {
        log.error("运行时异常:----------------{}", e.getMessage());
    }

    /**
     * 捕捉空指针异常
     *
     * @author Iwlthxcl
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = NullPointerException.class)
    public void handler (NullPointerException e) {
        log.error("空指针异常:----------------{}", e.getMessage());
    }

}
