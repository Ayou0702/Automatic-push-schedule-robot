package com.enterprise.service;

/**
 * 负责enterpriseData表业务的接口
 *
 * @author Iwlthxcl
 * @version 1.0
 */
public interface EnterpriseDataService {

    /**
     * 获取指定名称的数据
     *
     * @author Iwlthxcl
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    String queryingEnterpriseData(String dataName);

    /**
     * 修改指定名称的数据
     *
     * @author Iwlthxcl
     *
     * @param dataName 要修改的参数名称
     * @param dataValue 要修改的参数值
     */
    void updateEnterpriseData(String dataName, String dataValue);

}
