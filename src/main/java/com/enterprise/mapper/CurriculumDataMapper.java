package com.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enterprise.entity.CurriculumData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 线性课程表数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface CurriculumDataMapper extends BaseMapper<CurriculumData> {

    /**
     * 新增课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要新增的课程推送队列数据
     * @return 返回新增结果
     */
    @Insert("INSERT INTO curriculum_data(course_id,course_name,course_venue,course_specialized,teacher_id,teacher_name,teacher_phone,teacher_institute,teacher_specialized,curriculum_period,curriculum_week,curriculum_section) VALUES (#{courseId},#{courseName},#{courseVenue},#{courseSpecialized},#{teacherId},#{teacherName},#{teacherPhone},#{teacherInstitute},#{teacherSpecialized},#{curriculumPeriod},#{curriculumWeek},#{curriculumSection})")
    boolean addCurriculumData(CurriculumData curriculumData);

    /**
     * 每页100，分页删除所有课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回删除结果
     */
    @Delete("DELETE FROM curriculum_data LIMIT 100")
    boolean deleteAllCurriculumData();

    /**
     * 删除指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 需要删除的课程推送队列数据的序列ID
     * @return 返回删除结果
     */
    @Delete("DELETE FROM curriculum_data WHERE curriculum_id=#{curriculumId}")
    boolean deleteCurriculumDataByCurriculumId(int curriculumId);

    /**
     * 修改指定序列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumData 需要修改的课程推送队列数据
     * @return 返回修改结果
     */
    @Update("UPDATE curriculum_data SET course_name=#{courseName},course_venue=#{courseVenue},teacher_name=#{teacherName},course_specialized=#{courseSpecialized},curriculum_period=#{curriculumPeriod},curriculum_week=#{curriculumWeek},curriculum_section=#{curriculumSection} WHERE curriculum_id=${curriculumId}")
    boolean updateCurriculumData(CurriculumData curriculumData);

    /**
     * 每页100，分页查询所有的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM curriculum_data LIMIT 100")
    List<CurriculumData> queryAllCurriculumData();

    /**
     * 查询指定队列ID的课程推送队列数据
     *
     * @author PrefersMin
     *
     * @param curriculumId 队列ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM curriculum_data WHERE curriculum_id=#{curriculumId}")
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
    @Select("SELECT * FROM curriculum_data WHERE curriculum_period > #{curriculumPeriod} OR (curriculum_period >= #{curriculumPeriod} AND curriculum_week >= #{curriculumWeek}) LIMIT #{limit} OFFSET #{offset}")
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
    @Select("SELECT * FROM curriculum_data WHERE curriculum_period > #{curriculumPeriod} OR (curriculum_period >= #{curriculumPeriod} AND curriculum_week >= #{curriculumWeek})")
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
    @Select("SELECT COUNT(*) FROM curriculum_data WHERE curriculum_period > #{curriculumPeriod} OR (curriculum_period >= #{curriculumPeriod} AND curriculum_week >= #{curriculumWeek})")
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
    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek}")
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
    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek} AND curriculum_section=#{curriculumSection}")
    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

}
