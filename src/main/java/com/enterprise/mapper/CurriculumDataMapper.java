package com.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ScheduleInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CurriculumDataMapper extends BaseMapper<CurriculumData> {

    @Select("SELECT * FROM curriculum_data LIMIT 100")
    List<CurriculumData> queryAllCurriculumData();

    @Select("SELECT * FROM curriculum_data WHERE curriculum_period > #{curriculumPeriod} OR (curriculum_period >= #{curriculumPeriod} AND curriculum_week >= #{curriculumWeek}) LIMIT #{limit} OFFSET #{offset}")
    List<CurriculumData> queryNowCurriculumData(int curriculumPeriod,int curriculumWeek,int limit,int offset);

    @Select("SELECT COUNT(*) FROM curriculum_data WHERE curriculum_period > #{curriculumPeriod} OR (curriculum_period >= #{curriculumPeriod} AND curriculum_week >= #{curriculumWeek})")
    int queryNowCurriculumDataCount(int curriculumPeriod,int curriculumWeek);

    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek}")
    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek} AND curriculum_section=#{curriculumSection}")
    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

    @Insert("INSERT INTO curriculum_data(course_name,course_venue,course_avatar,course_specialized,teacher_name,teacher_phone,teacher_institute,teacher_avatar,teacher_specialized,curriculum_period,curriculum_week,curriculum_section) VALUES (#{courseName},#{courseVenue},#{courseAvatar},#{courseSpecialized},#{teacherName},#{teacherPhone},#{teacherInstitute},#{teacherAvatar},#{teacherSpecialized},#{curriculumPeriod},#{curriculumWeek},#{curriculumSection})")
    boolean addCurriculumData(CurriculumData curriculumData);

    @Delete("DELETE FROM curriculum_data LIMIT 100")
    boolean deleteAllCurriculumData();

    @Delete("DELETE FROM curriculum_data WHERE curriculum_id=#{curriculumId}")
    boolean deleteCurriculumDataByCurriculumId(int curriculumId);

}
