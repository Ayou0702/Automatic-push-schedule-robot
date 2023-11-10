package com.enterprise.service.data.entity.impl;

import com.enterprise.vo.data.entity.CurriculumData;
import com.enterprise.mapper.data.entity.CurriculumDataMapper;
import com.enterprise.service.data.entity.CurriculumDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 线性课程表数据接口实现类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Service
@RequiredArgsConstructor
public class CurriculumDataServiceImpl implements CurriculumDataService {

    /**
     * 线性课程表数据接口
     */
    private final CurriculumDataMapper curriculumDataMapper;

    /**
     * 新增课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要新增的课程推送队列数据
     * @return 返回新增结果
     */
    @Override
    public boolean addCurriculumData(CurriculumData curriculumData) {
        return curriculumDataMapper.addCurriculumData(curriculumData);
    }

    /**
     * 每页100，分页删除所有课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回删除结果
     */
    @Override
    public boolean deleteAllCurriculumData() {
        return curriculumDataMapper.deleteAllCurriculumData();
    }

    /**
     * 删除指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 需要删除的课程推送队列数据的序列ID
     * @return 返回删除结果
     */
    @Override
    public boolean deleteCurriculumDataByCurriculumId(int curriculumId) {
        return curriculumDataMapper.deleteCurriculumDataByCurriculumId(curriculumId);
    }

    /**
     * 修改指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要修改的课程推送队列数据
     * @return 返回修改结果
     */
    @Override
    public boolean updateCurriculumData(CurriculumData curriculumData) {
        return curriculumDataMapper.updateCurriculumData(curriculumData);
    }

    /**
     * 每页100，分页查询所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<CurriculumData> queryAllCurriculumData() {
        return curriculumDataMapper.queryAllCurriculumData();
    }

    /**
     * 查询指定队列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 队列ID
     * @return 返回查询结果
     */
    @Override
    public CurriculumData queryCurriculumDataByCurriculumId(int curriculumId){
        return curriculumDataMapper.queryCurriculumDataByCurriculumId(curriculumId);
    }

    /**
     * 查询指定周期和星期之后指定页大小与页数的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @param limit 页大小
     * @param offset 页数
     * @return 返回查询结果
     */
    @Override
    public List<CurriculumData> queryNowCurriculumData(int curriculumPeriod, int curriculumWeek, int limit, int offset) {
        return curriculumDataMapper.queryNowCurriculumData(curriculumPeriod, curriculumWeek, limit, offset);
    }

    /**
     * 查询指定周期和星期之后所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回查询结果
     */
    @Override
    public List<CurriculumData> getAllNowCurriculumData(int curriculumPeriod, int curriculumWeek) {
        return curriculumDataMapper.getAllNowCurriculumData(curriculumPeriod, curriculumWeek);
    }

    /**
     * 计次指定周期和星期之后所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回满足查询条件的记录数
     */
    @Override
    public int queryNowCurriculumDataCount(int curriculumPeriod, int curriculumWeek) {
        return curriculumDataMapper.queryNowCurriculumDataCount(curriculumPeriod, curriculumWeek);
    }

    /**
     * 查询指定周期和星期的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回查询结果
     */
    @Override
    public List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek) {
        return curriculumDataMapper.queryCurriculumDataByTime(curriculumPeriod, curriculumWeek);
    }

    /**
     * 查询指定周期、星期以及节次的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @param curriculumSection 课程节次
     * @return 返回查询结果
     */
    @Override
    public CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection) {
        return curriculumDataMapper.preciseQueryCurriculumDataByTime(curriculumPeriod, curriculumWeek, curriculumSection);
    }

}
