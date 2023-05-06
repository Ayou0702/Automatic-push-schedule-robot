package com.enterprise.service;

import com.enterprise.entity.CurriculumData;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CurriculumDataService {

    List<CurriculumData> queryAllCurriculumData();

    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

    boolean addCurriculumData(CurriculumData curriculumData);

    boolean deleteAllCurriculumData();

}
