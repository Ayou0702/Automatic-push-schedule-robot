package com.enterprise.controller.data;

import com.enterprise.common.handler.Result;
import com.enterprise.entity.EnterpriseData;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责配置数据的Controller
 *
 * @author PrefersMin
 * @version 1.6
 */
@RestController
@RequiredArgsConstructor
public class EnterpriseDataController {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 获取所有的配置数据
     *
     * @author PrefersMin
     *
     * @return 返回获取到的配置数据
     */
    @GetMapping("/getEnterpriseData")
    public Result getEnterpriseData() {

        List<EnterpriseData> enterpriseDataList = enterpriseDataService.queryingAllEnterpriseData();

        if (enterpriseDataList == null) {
            return Result.failed().message("配置数据加载失败");
        }

        return Result.success().message("配置数据加载成功").data("enterpriseDataList", enterpriseDataList);

    }

    /**
     * 修改指定配置项
     *
     * @author PrefersMin
     *
     * @param enterpriseDataList 需要修改的配置项数据
     * @return 返回修改结果
     */
    @PostMapping("/updateEnterpriseData")
    public Result updateEnterpriseData(@RequestBody List<EnterpriseData> enterpriseDataList) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        // 记录操作结果
        List<String> failedRecord = new ArrayList<>();

        for (EnterpriseData enterpriseData : enterpriseDataList) {

            boolean updateResult = enterpriseDataService.updateEnterpriseData(enterpriseData);

            if (!updateResult) {
                LogUtil.error("修改配置数据失败，数据项名称：" + enterpriseData.getDataName() + "，数据项值：" + enterpriseData.getDataValue());
                failedRecord.add("名称为" + enterpriseData.getDataName() + "的配置数据修改失败");

            }
        }

        if (!failedRecord.isEmpty()) {
            failedRecord.forEach(LogUtil::error);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return Result.failed().message("修改配置数据失败,操作已回滚").data("failedRecord",failedRecord);
        }

        LogUtil.info("配置数据修改成功");

        // 提交事务
        platformTransactionManager.commit(transactionStatus);

        return Result.success().message("修改配置数据成功").description("配置数据已更新");

    }

}
