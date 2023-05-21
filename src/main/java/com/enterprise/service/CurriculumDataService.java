package com.enterprise.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enterprise.entity.CurriculumData;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CurriculumDataService {

    List<CurriculumData> queryAllCurriculumData();

    List<CurriculumData> queryNowCurriculumData(int curriculumPeriod,int curriculumWeek,int limit,int offset);

    int queryNowCurriculumDataCount(int curriculumPeriod,int curriculumWeek);

    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

    boolean addCurriculumData(CurriculumData curriculumData);

    boolean deleteAllCurriculumData();

    boolean deleteCurriculumDataByCurriculumId(int curriculumId);

}
