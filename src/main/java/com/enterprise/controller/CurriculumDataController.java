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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

        List<CurriculumData> curriculumDataList = curriculumDataService.queryAllCurriculumData();

        if (curriculumDataList == null) {
            return result.failed("课程推送队列数据加载失败");
        }

        return result.success("课程推送队列数据加载成功", curriculumDataList);

    }

    @GetMapping("/resetCurriculumData")
    public ResultVo resetCurriculumData() {
        try {
            curriculumDataUtil.resetCurriculumData();
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }
        return result.success(200, "重置成功", "课程推送队列重置成功");
    }

    @PostMapping("/queryNowCurriculumData")
    public ResultVo queryNowCurriculumData(@RequestBody Integer pageIndex) {

        int week = dateUtil.getW();
        int period = dateUtil.getPeriod();

        int pageSize = 6;

        LogUtil.info("当前查询的是 " + period + "周 星期" + week + " 及以后的" + pageSize + "条课程");

        List<CurriculumData> curriculumDataList = curriculumDataService.queryNowCurriculumData(period, week, 6, pageIndex * pageSize);

        if (curriculumDataList == null) {
            return result.failed("课程推送队列数据加载失败");
        }

        PageVo pageVo = new PageVo();

        pageVo.setPageSize(pageSize);
        pageVo.setPageIndex(pageIndex * pageSize);
        pageVo.setTotalCount(curriculumDataService.queryNowCurriculumDataCount(period, week));
        pageVo.setCurriculumDataList(curriculumDataList);

        return result.success("课程推送队列数据加载成功", pageVo);

    }

    @GetMapping("/getAllNowCurriculumData")
    public ResultVo getAllNowCurriculumData() {

        int week = dateUtil.getW();
        int period = dateUtil.getPeriod();

        LogUtil.info("当前查询的是 " + period + "周 星期" + week + " 及以后的所有课程");

        List<CurriculumData> curriculumDataList = curriculumDataService.getAllNowCurriculumData(period, week);

        if (curriculumDataList == null) {
            return result.failed("课程推送队列数据加载失败");
        }

        return result.success("课程推送队列数据加载成功", curriculumDataList);
    }

    @Transactional
    @PostMapping("/deleteCurriculumData")
    public ResultVo deleteCurriculumData(@RequestBody List<Integer> curriculumIdList) {
        try {
            for (Integer curriculumId : curriculumIdList) {
                boolean deleteResult = curriculumDataService.deleteCurriculumDataByCurriculumId(curriculumId);
                if (!deleteResult) {
                    LogUtil.error("ID为" + curriculumId + "的课程推送队列数据删除失败");
                    throw new Exception("序列ID为" + curriculumId + "的课程推送队列数据删除失败");
                } else {
                    LogUtil.info("ID为" + curriculumId + "的课程推送队列数据删除成功");
                }
            }
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed(400, "删除失败", e.getMessage());
        }
        return result.success(200, "删除成功", curriculumIdList.size() + "条课程推送队列数据被删除");
    }

    @Transactional
    @PostMapping("/addCurriculumData")
    public ResultVo addCurriculumData(@RequestBody CurriculumData curriculumData) {

        try {

            boolean duplicateResult = curriculumDataService.preciseQueryCurriculumDataByTime(curriculumData.getCurriculumPeriod(), curriculumData.getCurriculumWeek(), curriculumData.getCurriculumSection()) == null;

            if (!duplicateResult) {
                LogUtil.error("课程时间冲突： " + curriculumData.getCurriculumPeriod() + " 周、星期 " + curriculumData.getCurriculumWeek() + "、第 " + curriculumData.getCurriculumSection() + " 节");
                LogUtil.error("课程推送队列数据：" + curriculumData);
                throw new Exception("新增的课程推送队列数据与已有数据存在推送时间冲突");
            }

            boolean insertResult = curriculumDataService.addCurriculumData(curriculumData);

            if (!insertResult) {
                LogUtil.error("名称为" + curriculumData.getCourseName() + "的课程推送队列数据新增失败");
                LogUtil.error("课程推送队列数据：" + curriculumData);
                throw new Exception("新增课程推送队列数据失败");
            } else {
                LogUtil.info("名称为" + curriculumData.getCourseName() + "的课程推送队列数据新增成功");
                return result.success(200, "新增成功", "新增了1条课程推送队列数据");
            }

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed(400, "新增失败", e.getMessage());
        }

    }

    @Transactional
    @PostMapping("/updateCurriculumData")
    public ResultVo updateCurriculumData(@RequestBody CurriculumData curriculumData) {

        try {
            boolean updateResult = curriculumDataService.updateCurriculumData(curriculumData);

            if (!updateResult) {
                LogUtil.error("ID为" + curriculumData.getCurriculumId() + "的课程推送队列数据修改失败");
                LogUtil.error("课程推送队列数据：" + curriculumData);
                throw new Exception("修改课程推送队列数据失败");
            } else {
                LogUtil.info("ID为" + curriculumData.getCurriculumId() + "的课程推送队列数据修改成功");
                return result.success(200, "修改成功", "修改了1条课程推送队列数据");
            }

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed(e.getMessage());
        }

    }

}
