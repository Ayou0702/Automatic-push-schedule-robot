package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 异常对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class ExceptionVo {

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 错误状态
     */
    private String errorState;

    /**
     * 错误代码
     */
    private int errorCode;

}
