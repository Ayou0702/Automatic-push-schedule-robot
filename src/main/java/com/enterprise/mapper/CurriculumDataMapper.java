package com.enterprise.mapper;

import com.enterprise.entity.CurriculumData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CurriculumDataMapper {

    @Select("SELECT * FROM curriculum_data")
    List<CurriculumData> queryAllCurriculumData();

    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek}")
    List<CurriculumData> queryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek);

    @Select("SELECT * FROM curriculum_data WHERE curriculum_period=#{curriculumPeriod} AND curriculum_week=#{curriculumWeek} AND curriculum_section=#{curriculumSection}")
    CurriculumData preciseQueryCurriculumDataByTime(int curriculumPeriod, int curriculumWeek, int curriculumSection);

    @Insert("INSERT INTO curriculum_data(course_id,teacher_id,curriculum_period,curriculum_week,curriculum_section) VALUES (#{courseId},#{teacherId},#{curriculumPeriod},#{curriculumWeek},#{curriculumSection})")
    boolean addCurriculumData(CurriculumData curriculumData);

    @Delete("DELETE FROM curriculum_data LIMIT 100")
    boolean deleteAllCurriculumData();

}
