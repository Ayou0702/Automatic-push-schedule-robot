package com.enterprise.entity;

import lombok.Data;

/**
 * enterpriseData表的实体类
 *
 * @author Iwlthxcl
 * @version 1.0
 */
@Data
public class EnterpriseData {

    /**
     * enterpriseData表的字段,转换驼峰命名
     */
    private String dataName, dataValue;

}
