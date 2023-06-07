package com.enterprise.util.enums;

/**
 * 推送模式的枚举类
 *
 * @author PrefersMin
 * @version 1.0
 */
public enum PushMode {

    // 日夜推送模式
    DAY(0, "晨间推送"),
    NIGHT(1, "夜间推送");

    private final int value;
    private final String desc;

    PushMode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return desc;
    }

}

