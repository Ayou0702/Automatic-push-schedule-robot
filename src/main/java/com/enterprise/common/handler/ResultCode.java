package com.enterprise.common.handler;

import lombok.Getter;

/**
 * 返回结果的枚举类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Getter
public enum ResultCode {

    SUCCESS(200,"请求成功"),

    FAILED(400,"请求失败"),

    SESSION_INVALID(701,"会话失效");

    private final int code;

    private final String desc;

    ResultCode(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

}
