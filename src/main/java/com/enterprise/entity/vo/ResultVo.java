package com.enterprise.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 统一返回对象
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
@Component
public class ResultVo {

    private int code;
    private String message;
    private Object data;

}
