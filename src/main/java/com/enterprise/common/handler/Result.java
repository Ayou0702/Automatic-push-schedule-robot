package com.enterprise.common.handler;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    /**
     * 返回结果
     */
    private Boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

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
    public static Result OK() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("访问成功");
        return result;
    }

    /**
     * 失败方法
     */
    public static Result Error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("访问失败");
        return result;
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
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

}
