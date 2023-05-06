package com.enterprise.service.impl;

import com.enterprise.entity.EnterpriseData;
import com.enterprise.mapper.EnterpriseDataMapper;
import com.enterprise.service.EnterpriseDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 负责enterpriseData表业务的接口实现类
 *
 * @author PrefersMin
 * @version 1.1
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
     * @author PrefersMin
     *
     * @param dataName 要获取的参数名称
     * @return 返回参数值
     */
    @Override
    public EnterpriseData queryingEnterpriseData(String dataName) {
        return enterpriseDataMapper.queryingEnterpriseData(dataName);
    }

    /**
     * 获取enterpriseData表中的所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回所有配置数据
     */
    @Override
    public List<EnterpriseData> queryingAllEnterpriseData() {
        return enterpriseDataMapper.queryingAllEnterpriseData();
    }

    /**
     * 修改指定名称的数据
     *
     * @author PrefersMin
     *
     * @param dataName 要修改的参数名称
     * @param dataValue 要修改的参数值
     */
    @Override
    public void updateEnterpriseDataByDataName(String dataName, String dataValue) {

        boolean state = enterpriseDataMapper.updateEnterpriseDataByDataName(dataName, dataValue);

        if (state) {
            System.out.println(dataName + "修改失败");
        }

    }

    /**
     * 修改指定名称的数据
     * 这里的结果需要返回给前端
     *
     * @param enterpriseData  需要修改的配置数据
     *
     * @author PrefersMin
     */
    @Override
    public boolean updateEnterpriseData(EnterpriseData enterpriseData) {
        return enterpriseDataMapper.updateEnterpriseData(enterpriseData);
    }

}
