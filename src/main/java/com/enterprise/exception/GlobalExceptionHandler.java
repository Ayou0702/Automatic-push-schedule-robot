package com.enterprise.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 * NullPointerException：空指针异常
 * RuntimeException：捕捉其他异常
 */
@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public void handler (RuntimeException e) {
        log.error("运行时异常:----------------{}", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NullPointerException.class)
    public void handler (NullPointerException e) {
        log.error("空指针异常:----------------{}", e.getMessage());
    }

}
