package com.enterprise.common.handler;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回结果
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class Result {

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息描述
     */
    private String description;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 私有构造方法
     */
    private Result() {
    }

    /**
     * 成功方法
     */
    public static Result success() {
        Result result = new Result();
        result.setState(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    /**
     * 失败方法
     */
    public static Result failed() {
        Result result = new Result();
        result.setState(false);
        result.setCode(ResultCode.FAILED.getCode());
        return result;
    }

    public Result state(Boolean state) {
        this.setState(state);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result description(String description) {
        this.setDescription(description);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public Result resultCode(ResultCode resultCode) {
        this.setCode(resultCode.getCode());
        this.setDescription(resultCode.getDesc());
        return this;
    }

}
