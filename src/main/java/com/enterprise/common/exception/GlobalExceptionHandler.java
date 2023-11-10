package com.enterprise.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.enterprise.common.handler.Result;
import com.enterprise.common.handler.ResultCode;
import com.enterprise.util.LogUtil;
import com.enterprise.vo.enums.SaTokenCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 *
 * @author PrefersMin
 * @version 1.1
 */
@ControllerAdvice
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 捕捉运行时异常
     *
     * @author PrefersMin
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        LogUtil.error("运行时异常:" + e.getMessage());
        return Result.failed().message("系统异常，请联系管理员");
    }

    /**
     * 捕捉空指针异常
     *
     * @author PrefersMin
     *
     * @param e 异常内容
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result handler(NullPointerException e) {
        LogUtil.error("空指针异常:" + e.getMessage());
        return Result.failed().message("系统异常，请联系管理员");
    }

    /**
     * Sa-Token登录异常处理
     *
     * @param nle 异常对象
     * @return 统一接口返回值
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result notLoginException(NotLoginException nle) {

        String message = SaTokenCode.getDescByType(nle.getType());

        LogUtil.error(message);

        return Result.failed().resultCode(ResultCode.SESSION_INVALID).message(message);

    }

}
