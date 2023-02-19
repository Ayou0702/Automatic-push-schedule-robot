package com.enterprise.service;

import com.enterprise.mapper.EnterpriseDataMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 负责enterpriseData表业务的接口实现类
 */
@Service
public class EnterpriseDataServiceImpl implements EnterpriseDataService {

    /**
     * 负责enterpriseData表的数据交互
     */
    @Resource
    private EnterpriseDataMapper enterpriseDataMapper;

    /**
     * 获取指定名称的数据
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    @Override
    public String queryingEnterpriseData (String dataName) {
        return enterpriseDataMapper.queryingEnterpriseData(dataName);
    }

    /**
     * 修改指定名称的数据
     *
     * @param dataName  要修改的参数名称
     * @param dataValue 要修改的参数值
     * @return 返回一个boolean表示修改成功与否
     */
    @Override
    public boolean updateEnterpriseData (String dataName, String dataValue) {
        return enterpriseDataMapper.updateEnterpriseData(dataName, dataValue) > 0;

    }

}
