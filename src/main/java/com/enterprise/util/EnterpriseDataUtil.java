package com.enterprise.util;

import com.enterprise.service.data.entity.EnterpriseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 配置数据工具类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Component
@RequiredArgsConstructor
public class EnterpriseDataUtil {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    public void dataIncrement(String dataName) {

        // 获取当前数据
        int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData(dataName).getDataValue());
        // 自增
        temp++;
        // 回写
        enterpriseDataService.updateEnterpriseDataByDataName(dataName, String.valueOf(temp));


    }

}
