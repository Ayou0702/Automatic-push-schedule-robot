package com.enterprise.service.data.entity.impl;

import com.enterprise.vo.data.entity.EnterpriseData;
import com.enterprise.mapper.data.entity.EnterpriseDataMapper;
import com.enterprise.service.data.entity.EnterpriseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置数据接口实现类
 *
 * @author PrefersMin
 * @version 1.5
 */
@Service
@RequiredArgsConstructor
public class EnterpriseDataServiceImpl implements EnterpriseDataService {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataMapper enterpriseDataMapper;

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
    @Override
    public boolean updateEnterpriseDataByDataName(String dataName, String dataValue) {
        return enterpriseDataMapper.updateEnterpriseDataByDataName(dataName, dataValue);
    }

    /**
     * 修改指定配置项的配置数据
     *
     * @author PrefersMin
     *
     * @param enterpriseData  需要修改的配置数据
     * @return 返回修改结果
     */
    @Override
    public boolean updateEnterpriseData(EnterpriseData enterpriseData) {
        return enterpriseDataMapper.updateEnterpriseData(enterpriseData);
    }

    /**
     * 查询指定配置名称的配置数据
     *
     * @author PrefersMin
     *
     * @param dataName 配置名称
     * @return 返回查询结果
     */
    @Override
    public EnterpriseData queryingEnterpriseData(String dataName) {
        return enterpriseDataMapper.queryingEnterpriseData(dataName);
    }

    /**
     * 查询所有配置数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<EnterpriseData> queryingAllEnterpriseData() {
        return enterpriseDataMapper.queryingAllEnterpriseData();
    }

}