package com.enterprise.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 返回对象实体类
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
