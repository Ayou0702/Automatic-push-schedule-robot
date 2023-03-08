package com.enterprise.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:47
 */
@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler {

    /**
     * 捕捉运行时异常
     *
     * @param e 异常内容
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:48
     */
    @ExceptionHandler(value = RuntimeException.class)
    public void handler (RuntimeException e) {
        log.error("运行时异常:----------------{}", e.getMessage());
    }

    /**
     * 捕捉空指针异常
     *
     * @param e 异常内容
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:48
     */
    @ExceptionHandler(value = NullPointerException.class)
    public void handler (NullPointerException e) {
        log.error("空指针异常:----------------{}", e.getMessage());
    }

}
