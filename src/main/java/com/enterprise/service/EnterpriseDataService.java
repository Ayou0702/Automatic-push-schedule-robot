package com.enterprise.service;

import com.enterprise.entity.EnterpriseData;
import java.util.List;

/**
 * 负责enterpriseData表业务的接口
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface EnterpriseDataService {

    /**
     * 获取指定名称的数据
     *
     * @author PrefersMin
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    EnterpriseData queryingEnterpriseData(String dataName);

    /**
     * 获取enterpriseData表中的所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回所有配置数据
     */
    List<EnterpriseData> queryingAllEnterpriseData();

    /**
     * 修改指定名称的数据
     *
     * @author PrefersMin
     *
     * @param dataName 要修改的参数名称
     * @param dataValue 要修改的参数值
     */
    void updateEnterpriseDataByDataName(String dataName, String dataValue);

    /**
     * 修改指定名称的数据
     *
     * @author PrefersMin
     *
     * @param enterpriseData  需要修改的配置数据
     * @return 返回受影响的行数，若为 -1 则操作失败
     */
    boolean updateEnterpriseData(EnterpriseData enterpriseData);
}
