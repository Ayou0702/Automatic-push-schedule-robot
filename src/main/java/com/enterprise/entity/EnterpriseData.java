package com.enterprise.entity;

import lombok.Data;

/**
 * 配置数据对象实体类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Data
public class EnterpriseData {

    /**
     * enterpriseData表的字段,转换驼峰命名
     */
    private String dataName, dataValue, dataAnnotation;

}
