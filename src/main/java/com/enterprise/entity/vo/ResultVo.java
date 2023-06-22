package com.enterprise.entity.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 返回对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
@Component
public class ResultVo {

    /**
     * 状态码
     */
    private int code;

    /**
     * 结果摘要
     */
    private String message;

    /**
     * 结果数据
     */
    private Object data;

}
