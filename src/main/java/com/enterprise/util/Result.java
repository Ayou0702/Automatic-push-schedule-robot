package com.enterprise.util;

import com.enterprise.entity.vo.ResultVo;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 封装类、统一返回格式
 *
 * @author PrefersMin
 * @version 1.2
 */
@Component
public class Result implements Serializable {

    /**
     * 返回对象实体类
     */
    private final ResultVo resultVo;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param resultVo 返回对象实体类
     */
    public Result(ResultVo resultVo) {
        this.resultVo = resultVo;
    }

    public ResultVo success(String message,Object data) {
        return success(200, message, data);
    }

    public ResultVo success(String message) {
        return success(200, message, null);
    }

    public ResultVo success(int code, String message, Object data) {

        resultVo.setCode(code);
        resultVo.setMessage(message);
        resultVo.setData(data);
        return resultVo;

    }

    public ResultVo failed(String message) {
        return failed(400, message, null);
    }

    public ResultVo failed(int code, String message) {
        return failed(code, message, null);
    }

    public ResultVo failed(int code, String message, Object data) {

        resultVo.setCode(code);
        resultVo.setMessage(message);
        resultVo.setData(data);
        return resultVo;

    }
}

