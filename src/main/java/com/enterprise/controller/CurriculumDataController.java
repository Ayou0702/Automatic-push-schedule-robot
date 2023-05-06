package com.enterprise.controller;

import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CurriculumDataService;
import com.enterprise.util.CurriculumDataUtil;
import com.enterprise.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CurriculumDataController {

    @Resource
    Result result;

    @Resource
    CurriculumDataUtil curriculumDataUtil;

    @Resource
    CurriculumDataService curriculumDataService;

    @GetMapping("/queryCurriculumData")
    public ResultVo queryCurriculumData() {

        List<CurriculumData> curriculumData = curriculumDataService.queryAllCurriculumData();

        if (curriculumData.isEmpty()) {
            return result.failed("课表数据加载失败");
        }

        return result.success("课表数据加载成功", curriculumData);

    }

    @GetMapping("/resetCurriculumData")
    public void resetCurriculumData() {
        curriculumDataUtil.resetCurriculumData();
    }

}
