package com.enterprise.service;

/**
 * 负责enterpriseData表业务的接口
 */
public interface EnterpriseDataService {

    /**
     * 获取指定名称的数据
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    String queryingEnterpriseData (String dataName);

    /**
     * 修改指定名称的数据
     *
     * @param dataName  要修改的参数名称
     * @param dataValue 要修改的参数值
     * @return 返回一个boolean表示修改成功与否
     */
    boolean updateEnterpriseData (String dataName, String dataValue);

}
