package com.enterprise.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import com.enterprise.common.handler.Result;
import com.enterprise.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 *
 * @author PrefersMin
 * @version 1.0
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
    public void handler(RuntimeException e) {
        LogUtil.error("运行时异常:----------------{}\n" + e.getMessage());
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
        LogUtil.error("空指针异常:----------------{}\n" + e.getMessage());
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
        // 打印堆栈，以供调试
        nle.printStackTrace();

        // 判断场景值，定制化异常信息
        String message;
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }
        return Result.failed().message(message);
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public Result notPermissionException(NotPermissionException npe) {
        npe.printStackTrace();
        return Result.failed().code(403).message("当前登录账号没有访问权限！");
    }

    @ExceptionHandler(SaTokenException.class)
    @ResponseBody
    public Result saTokenException(SaTokenException ste) {
        ste.printStackTrace();
        return Result.failed().code(401).message("请登录再执行此操作！");
    }

}
