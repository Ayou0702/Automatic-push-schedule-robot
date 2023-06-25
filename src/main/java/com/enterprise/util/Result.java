package com.enterprise.util;

import com.enterprise.entity.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 封装类、统一返回格式
 *
 * @author PrefersMin
 * @version 1.3
 */
@Component
@RequiredArgsConstructor
public class Result implements Serializable {

    /**
     * 返回对象实体类
     */
    private final ResultVo resultVo;

    public ResultVo success(String message,Object data) {
        return success(200, message, data);
    }

    public ResultVo success(String message) {
        return success(200, message, null);
    }

    public ResultVo success(Object data) {
        return success(200, "", data);
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

