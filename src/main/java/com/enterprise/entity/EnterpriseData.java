package com.enterprise.entity;

import lombok.Data;

/**
 * 配置数据对象实体类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Data
public class EnterpriseData {

    /**
     * 配置名称
     */
    private String dataName;

    /**
     * 配置数据
     */
    private String dataValue;

    /**
     * 配置注释
     */
    private String dataAnnotation;

}
