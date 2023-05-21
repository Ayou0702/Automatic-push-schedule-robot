package com.enterprise.service.impl;

import com.enterprise.entity.CurriculumData;
import com.enterprise.mapper.CurriculumDataMapper;
import com.enterprise.service.CurriculumDataService;
import com.enterprise.service.MultilistMapperService;
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
    public List<CurriculumData> queryNowCurriculumData(int curriculumPeriod, int curriculumWeek, int limit, int offset) {
        return curriculumDataMapper.queryNowCurriculumData(curriculumPeriod, curriculumWeek, limit, offset);
    }

    @Override
    public int queryNowCurriculumDataCount(int curriculumPeriod, int curriculumWeek) {
        return curriculumDataMapper.queryNowCurriculumDataCount(curriculumPeriod,curriculumWeek);
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

    @Override
    public boolean deleteCurriculumDataByCurriculumId(int curriculumId) {
        return curriculumDataMapper.deleteCurriculumDataByCurriculumId(curriculumId);
    }

}
