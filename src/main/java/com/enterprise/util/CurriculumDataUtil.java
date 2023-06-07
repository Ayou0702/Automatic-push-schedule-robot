package com.enterprise.util;

import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ScheduleInfo;
import com.enterprise.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class CurriculumDataUtil {

    /**
     * 分割字符
     */
    final String splitString = "-";
    final ArrayList<CurriculumData> curriculumDataArrayList = new ArrayList<>();
    @Resource
    CurriculumDataService curriculumDataService;
    @Resource
    EnterpriseDataService enterpriseDataService;
    @Resource
    MultilistMapperService multilistMapperService;
    ScheduleInfo[][][] scheduleDataArray;

    @Transactional
    public void resetCurriculumData() {

        boolean isEmpty = true;

        while (isEmpty) {
            isEmpty = curriculumDataService.deleteAllCurriculumData();
        }

        List<ScheduleInfo> scheduleDataList = multilistMapperService.resetCurriculumData();

        scheduleDataArray = new ScheduleInfo[PushDataUtil.PERIOD_MAX][PushDataUtil.WEEK_MAX][PushDataUtil.SECTION_MAX];

        // 通过for循环将课表数据按照上课时间填入数组中
        scheduleDataList.forEach(scheduleData -> {

            // 读取每个课表数据中的开始时间和结束时间
            int[] period = getClassTime(scheduleData.getSchedulePeriod());
            int[] section = getClassTime(scheduleData.getScheduleSection());

            // 读取每个课表数据中的星期
            int week = Integer.parseInt(scheduleData.getScheduleWeek());

            // 以周期开始第一层循环
            period:
            for (int i = period[0]; i <= period[1]; i++) {

                // 以节次开始第二层循环
                for (int p = section[0]; p <= section[1]; p++) {
                    // 检测课表时间是否有冲突
                    if (!isNull(scheduleDataArray[i][week][p])) {
                        LogUtil.warn("课程表中有课程时间冲突，请检查");
                        LogUtil.warn("冲突课程名称：" + scheduleData.getCourseName() + ";冲突课程名称：" + scheduleDataArray[i][week][p].getCourseName());
                        // 直接跳出周期循环
                        break period;
                    }
                    // 将对应周期、星期、节次的课程数据写入数组
                    scheduleDataArray[i][week][p] = scheduleData;
                }

            }
        });

        for (int i = 0; i < scheduleDataArray.length; i++) {
            for (int j = 0; j < scheduleDataArray[i].length; j++) {
                for (int k = 0; k < scheduleDataArray[i][j].length; k++) {
                    if (!isNull(scheduleDataArray[i][j][k])) {

                        CurriculumData curriculumData = new CurriculumData();

                        curriculumData.setCourseName(scheduleDataArray[i][j][k].getCourseName());
                        curriculumData.setCourseVenue(scheduleDataArray[i][j][k].getCourseVenue());
                        curriculumData.setCourseSpecialized(scheduleDataArray[i][j][k].isCourseSpecialized());
                        curriculumData.setCourseId(scheduleDataArray[i][j][k].getCourseId());

                        curriculumData.setTeacherName(scheduleDataArray[i][j][k].getTeacherName());
                        curriculumData.setTeacherPhone(scheduleDataArray[i][j][k].getTeacherPhone());
                        curriculumData.setTeacherInstitute(scheduleDataArray[i][j][k].getTeacherInstitute());
                        curriculumData.setTeacherId(scheduleDataArray[i][j][k].getTeacherId());
                        curriculumData.setTeacherSpecialized(scheduleDataArray[i][j][k].isCourseSpecialized());

                        curriculumData.setCurriculumPeriod(i);
                        curriculumData.setCurriculumWeek(j);
                        curriculumData.setCurriculumSection(k);

                        curriculumDataArrayList.add(curriculumData);

                    }
                }
            }
        }

        curriculumDataArrayList.forEach(curriculumDataService::addCurriculumData);

        curriculumDataArrayList.clear();
    }

    /**
     * 通过split方法分割开始时间与结束时间
     *
     * @author PrefersMin
     *
     * @param classStringTime 上课时间数据
     * @return 返回两个int型的数据作为开始时间与结束时间
     */
    public int[] getClassTime(String classStringTime) {

        // 开始时间与结束时间实体类
        int[] time = new int[2];

        // 判断是否属于时间段
        if (classStringTime.contains(splitString)) {

            // 以 "-" 符号分割开始时间与结束时间
            String[] temp = classStringTime.split("-");

            // 分别设置开始时间与结束时间
            time[0] = Integer.parseInt(temp[0]);
            time[1] = Integer.parseInt(temp[1]);

            // 返回封装好的开始时间与结束时间
            return time;

        }

        // 直接返回数据，并将其同时作为开始时间与结束时间
        time[0] = Integer.parseInt(classStringTime);
        time[1] = Integer.parseInt(classStringTime);

        return time;

    }

    /**
     * 返回今日课表信息
     *
     * @author PrefersMin
     *
     * @param period 周数
     * @param week 星期
     * @return 今日课表信息
     */
    public List<CurriculumData> getTodayCurriculumData(int period, int week) {

        // 判断是否是debug中，如不是则计算专业课程数
        if (!enterpriseDataService.queryingEnterpriseData("debugPushMode").getDataValue().equals(enterpriseDataService.queryingEnterpriseData("departmentId").getDataValue())) {
            // 根据courseInfo表中的totalSpecializedClassTimes字段判断今天是否有专业课程
            for (int i = 1; i < PushDataUtil.SECTION_MAX - 1; i++) {
                // 非空判断
                if (curriculumDataService.preciseQueryCurriculumDataByTime(period, week, i) != null) {
                    // 专业课程判断
                    if (curriculumDataService.preciseQueryCurriculumDataByTime(period, week, i).isCourseSpecialized()) {
                        // 获取已上课的专业课程次数
                        Integer totalSpecializedClassTimes = Integer.valueOf(enterpriseDataService.queryingEnterpriseData("totalSpecializedClassTimes").getDataValue());
                        // 自增
                        totalSpecializedClassTimes++;
                        // 回写
                        enterpriseDataService.updateEnterpriseDataByDataName("totalSpecializedClassTimes", String.valueOf(totalSpecializedClassTimes));
                    }
                }
            }
        }

        return curriculumDataService.queryCurriculumDataByTime(period, week);
    }

}
