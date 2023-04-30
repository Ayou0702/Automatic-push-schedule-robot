package com.enterprise.controller;

import com.enterprise.entity.EnterpriseData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责前端页面的配置数据
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
public class EnterpriseDataController {

  @Resource
  Result result;

  /**
   * enterpriseData的接口，用于读取查询企业微信配置数据
   */
  @Resource
  EnterpriseDataService enterpriseDataService;

  @GetMapping("/getEnterpriseData")
  public ResultVo getEnterpriseData() {

    List<EnterpriseData> enterpriseDataList = enterpriseDataService.queryingAllEnterpriseData();
    if (enterpriseDataList.isEmpty()) {
      return result.failed("配置数据加载失败");
    }
    return result.success("配置数据加载成功", enterpriseDataList);

  }

  @PostMapping("/updateEnterpriseData")
  @Transactional(rollbackFor = Exception.class)
  public ResultVo updateEnterpriseData(@RequestBody List<EnterpriseData> enterpriseDataList) {
    List<String> record = new ArrayList<>();
    try {
      for (EnterpriseData enterpriseData : enterpriseDataList) {
        boolean updateResult = enterpriseDataService.updateEnterpriseData(enterpriseData);
        if (!updateResult) {
          LogUtil.error("修改配置数据失败，数据项名称：" + enterpriseData.getDataName() + "，数据项值：" + enterpriseData.getDataValue());
          throw new Exception("修改配置数据失败,操作已回滚");
        }
        record.add("配置数据被修改，数据信息：" + enterpriseData);
      }
      record.forEach(LogUtil::info);

    } catch (Exception e) {
      LogUtil.error(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return result.failed(e.getMessage());
    }

    return result.success("配置数据修改成功");
  }


}
