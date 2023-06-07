package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 异常对象实体类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
public class ExceptionVo {

    String errorMessage,errorState;
    int errorCode;

}
