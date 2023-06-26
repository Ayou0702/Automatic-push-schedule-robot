package com.enterprise.common.handler;

public interface ResultCode {
    /**
     * 成功
     */
    Integer SUCCESS = 200;

    /**
     * 失败
     */
    Integer ERROR = 500;

    /**
     * 登录失效
     */
    Integer UNAUTHORIZED = 201;

    /**
     * 权限不足
     */
    Integer FORBIDDEN = 202;
}
