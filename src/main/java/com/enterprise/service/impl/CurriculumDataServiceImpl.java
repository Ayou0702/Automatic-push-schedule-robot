package com.enterprise.service.impl;

import com.enterprise.entity.CurriculumData;
import com.enterprise.mapper.CurriculumDataMapper;
import com.enterprise.service.CurriculumDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CurriculumDataServiceImpl implements CurriculumDataService {

    @Resource
    private CurriculumDataMapper curriculumDataMapper;

    @Override
    public List<CurriculumData> queryAllCurriculumData() {
        return curriculumDataMapper.queryAllCurriculumData();
    }

    @Override
    public List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek) {
        return curriculumDataMapper.queryCurriculumDataByTime(curriculumPeriod, curriculumWeek);
    }

    @Override
    public CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection) {
        return curriculumDataMapper.preciseQueryCurriculumDataByTime(curriculumPeriod, curriculumWeek, curriculumSection);
    }

    @Override
    public boolean addCurriculumData(CurriculumData curriculumData) {
        return curriculumDataMapper.addCurriculumData(curriculumData);
    }

    @Override
    public boolean deleteAllCurriculumData() {
        return curriculumDataMapper.deleteAllCurriculumData();
    }

}
