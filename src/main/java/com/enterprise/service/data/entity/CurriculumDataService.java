package com.enterprise.service.data.entity;

import com.enterprise.vo.data.entity.CurriculumData;

import java.util.List;

/**
 * 线性课程表数据接口
 *
 * @author PrefersMin
 * @version 1.2
 */
public interface CurriculumDataService {

    /**
     * 新增课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要新增的课程推送队列数据
     * @return 返回新增结果
     */
    boolean addCurriculumData(CurriculumData curriculumData);

    /**
     * 每页100，分页删除所有课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回删除结果
     */
    boolean deleteAllCurriculumData();

    /**
     * 删除指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 需要删除的课程推送队列数据的序列ID
     * @return 返回删除结果
     */
    boolean deleteCurriculumDataByCurriculumId(int curriculumId);

    /**
     * 修改指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要修改的课程推送队列数据
     * @return 返回修改结果
     */
    boolean updateCurriculumData(CurriculumData curriculumData);

    /**
     * 每页100，分页查询所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<CurriculumData> queryAllCurriculumData();

    /**
     * 查询指定队列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 队列ID
     * @return 返回查询结果
     */
    CurriculumData queryCurriculumDataByCurriculumId(int curriculumId);

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
    List<CurriculumData> queryNowCurriculumData(int curriculumPeriod, int curriculumWeek, int limit, int offset);

    /**
     * 查询指定周期和星期之后所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回查询结果
     */
    List<CurriculumData> getAllNowCurriculumData(int curriculumPeriod, int curriculumWeek);

    /**
     * 计次指定周期和星期之后所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回满足查询条件的记录数
     */
    int queryNowCurriculumDataCount(int curriculumPeriod, int curriculumWeek);

    /**
     * 查询指定周期和星期的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumPeriod 课程周期
     * @param curriculumWeek 课程星期
     * @return 返回查询结果
     */
    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

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
    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

}
