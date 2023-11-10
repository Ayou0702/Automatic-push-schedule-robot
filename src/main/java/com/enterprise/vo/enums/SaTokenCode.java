package com.enterprise.vo.enums;

import lombok.Getter;

/**
 * SaToken的枚举类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Getter
public enum SaTokenCode {

    NOT_TOKEN("-1","未提供Token"),

    INVALID_TOKEN("-2","Token无效"),

    TOKEN_TIMEOUT("-3","Token已过期"),

    BE_REPLACED("-4","账号在别处登录"),

    KICK_OUT("-5","您已被踢下线"),

    DEFAULT_MESSAGE("0","会话未登录");

    private final String type;

    private final String desc;

    SaTokenCode(String type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String type) {
        for (SaTokenCode value : SaTokenCode.values()) {
            if (type.equals(value.getType())) {
                return value.getDesc();
            }
        }
        return DEFAULT_MESSAGE.getDesc();
    }

}
