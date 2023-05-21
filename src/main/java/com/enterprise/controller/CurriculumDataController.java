package com.enterprise.controller;

import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.PageVo;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CurriculumDataService;
import com.enterprise.util.CurriculumDataUtil;
import com.enterprise.util.DateUtil;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class CurriculumDataController {

    @Resource
    Result result;

    @Resource
    DateUtil dateUtil;

    @Resource
    CurriculumDataUtil curriculumDataUtil;

    @Resource
    CurriculumDataService curriculumDataService;

    @GetMapping("/queryAllCurriculumData")
    public ResultVo queryAllCurriculumData() {

        List<CurriculumData> curriculumData = curriculumDataService.queryAllCurriculumData();

        if (curriculumData.isEmpty()) {
            return result.failed("课表数据加载失败");
        }

        return result.success("课表数据加载成功", curriculumData);

    }

    @GetMapping("/resetCurriculumData")
    public ResultVo resetCurriculumData() {
        try {
            curriculumDataUtil.resetCurriculumData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.success("重置课程推送队列成功");
    }

    @PostMapping("/queryNowCurriculumData")
    public ResultVo queryNowCurriculumData(@RequestBody Map<String, Integer> pageIndexMap) {

        int pageIndex = pageIndexMap.get("pageIndex");
        int week = dateUtil.getW();
        int period = dateUtil.getPeriod();

        System.out.println("当前查询的是 "+period+"周 星期"+week+" 及以后的课程");

        List<CurriculumData> curriculumDataList = curriculumDataService.queryNowCurriculumData(period, week, 6, pageIndex * 6);

        if (curriculumDataList.isEmpty()) {
            return result.failed("课表数据加载失败");
        }

        PageVo pageVo = new PageVo();

        pageVo.setPageSize(6);
        pageVo.setPageIndex(pageIndex * 6);
        pageVo.setTotalCount(curriculumDataService.queryNowCurriculumDataCount(period, week));
        pageVo.setCurriculumDataList(curriculumDataList);

        return result.success("课表数据加载成功", pageVo);

    }

    @Transactional
    @PostMapping("/deleteCurriculumData")
    public ResultVo deleteCurriculumData(@RequestBody Map<String, Integer> map) {

        int curriculumId = map.get("id");

        try {
            boolean deleteResult = curriculumDataService.deleteCurriculumDataByCurriculumId(curriculumId);
            if (!deleteResult) {
                LogUtil.error("序列ID为" + curriculumId + "的课表信息删除失败");
                throw new Exception("删除课表信息失败");
            } else {
                LogUtil.info("序列ID为" + curriculumId + "的课表信息被删除");
            }
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result.success("课表信息删除成功");
    }

}
