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

    private String errorMessage,errorState;
    private int errorCode;

}
