package com.enterprise.entity;

import lombok.Data;

/**
 * enterpriseData表的实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class EnterpriseData {

    /**
     * enterpriseData表的字段,转换驼峰命名
     */
    private String dataName, dataValue, dataAnnotation;

}
