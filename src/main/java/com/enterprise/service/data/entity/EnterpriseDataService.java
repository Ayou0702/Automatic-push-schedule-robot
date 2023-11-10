package com.enterprise.service.data.entity;

import com.enterprise.vo.data.entity.EnterpriseData;

import java.util.List;

/**
 * 配置数据接口
 *
 * @author PrefersMin
 * @version 1.3
 */
public interface EnterpriseDataService {

    /**
     * 修改指定配置项的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 配置项名称
     * @param dataValue 配置数据
     *
     * @return 返回修改结果
     */
    boolean updateEnterpriseDataByDataName(String dataName, String dataValue);

    /**
     * 修改指定配置项的配置数据
     *
     * @author PrefersMin
     *
     * @param enterpriseData  需要修改的配置数据
     * @return 返回修改结果
     */
    boolean updateEnterpriseData(EnterpriseData enterpriseData);

    /**
     * 查询指定配置名称的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 配置名称
     * @return 返回查询结果
     */
    EnterpriseData queryingEnterpriseData(String dataName);

    /**
     * 查询所有配置数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<EnterpriseData> queryingAllEnterpriseData();

}
