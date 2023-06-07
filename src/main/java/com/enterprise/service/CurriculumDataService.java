package com.enterprise.service;

import com.enterprise.entity.CurriculumData;

import java.util.List;

public interface CurriculumDataService {

    List<CurriculumData> queryAllCurriculumData();

    List<CurriculumData> queryNowCurriculumData(int curriculumPeriod,int curriculumWeek,int limit,int offset);

    List<CurriculumData> getAllNowCurriculumData(int curriculumPeriod,int curriculumWeek);

    boolean updateCurriculumData(CurriculumData curriculumData);

    int queryNowCurriculumDataCount(int curriculumPeriod,int curriculumWeek);

    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

    boolean addCurriculumData(CurriculumData curriculumData);

    boolean deleteAllCurriculumData();

    boolean deleteCurriculumDataByCurriculumId(int curriculumId);

}
