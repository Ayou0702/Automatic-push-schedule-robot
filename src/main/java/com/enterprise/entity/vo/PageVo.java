package com.enterprise.entity.vo;

import com.enterprise.entity.CurriculumData;
import lombok.Data;

import java.util.List;

@Data
public class PageVo {

    private int pageIndex, pageSize, totalCount;
    private List<CurriculumData> curriculumDataList;

}
