package com.enterprise.controller;

import com.enterprise.entity.EnterpriseData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 负责前端页面的配置数据
 *
 * @author PrefersMin
 * @version 1.1
 */
@RestController
public class EnterpriseDataController {

    /**
     * 封装返回结果
     */
    @Resource
    Result result;

    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    /**
     * 获取所有的配置数据
     *
     * @author PrefersMin
     *
     * @return 返回获取到的配置数据
     */
    @GetMapping("/getEnterpriseData")
    public ResultVo getEnterpriseData() {

        List<EnterpriseData> enterpriseDataList = enterpriseDataService.queryingAllEnterpriseData();

        if (enterpriseDataList == null) {
            return result.failed("配置数据加载成功");
        }

        return result.success("配置数据加载成功", enterpriseDataList);

    }

    /**
     * 修改指定配置项
     *
     * @author PrefersMin
     *
     * @param enterpriseData 需要修改的配置项数据
     * @return 返回修改结果
     */
    @PostMapping("/updateEnterpriseData")
    public ResultVo updateEnterpriseData(@RequestBody EnterpriseData enterpriseData) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {

            boolean updateResult = enterpriseDataService.updateEnterpriseData(enterpriseData);

            if (!updateResult) {
                LogUtil.error("修改配置数据失败，数据项名称：" + enterpriseData.getDataName() + "，数据项值：" + enterpriseData.getDataValue());
                throw new Exception("修改配置数据失败,操作已回滚");
            }

            LogUtil.info("配置数据被修改，数据信息：" + enterpriseData);
            // 提交事务
            platformTransactionManager.commit(transactionStatus);

            return result.success(200,"配置数据修改成功","名称为 " + enterpriseData.getDataName() + " 的配置数据修改成功");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.failed(400,"修改失败",e.getMessage());
        }

    }

}
