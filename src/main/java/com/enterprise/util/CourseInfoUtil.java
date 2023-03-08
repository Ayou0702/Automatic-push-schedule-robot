package com.enterprise.util;

import com.enterprise.entity.CourseInfo;
import com.enterprise.entity.vo.CourseSectionVo;
import com.enterprise.entity.vo.CourseStartAndEndTimeVo;
import com.enterprise.service.CourseInfoServiceImpl;
import com.enterprise.service.EnterpriseDataServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * 获取课程信息的工具类
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:56
 */
@Component
public class CourseInfoUtil {

    // 五大节课程实体类
    final CourseSectionVo courseSectionVo = new CourseSectionVo();
    // 声明课表数组
    CourseInfo[][][] schedule;

    /**
     * 工具类
     */
    @Resource
    private EnterpriseDataServiceImpl enterpriseDataService;
    @Resource
    private CourseInfoServiceImpl courseInfoService;

    /**
     * 更新课表数据
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:56
     */
    public void updateCourseInfo () {

        // 创建并清空课表数据
        schedule = new CourseInfo[23][8][6];

        // 获取课程数据
        List<CourseInfo> temp = getCourseInfos();

        // foreach循环将课程数据填入课表数据
        for (CourseInfo courseInfo : temp) {
            createClassSchedule(courseInfo);
        }

    }

    /**
     * 获取所有课程数据
     *
     * @return 返回课程数据
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:56
     */
    public List<CourseInfo> getCourseInfos () {

        // 通过Service层获取数据
        return courseInfoService.queryCourse();

    }

    /**
     * 通过for循环将课表数据按照上课时间填入数组中
     *
     * @param courseInfo 课表数据
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:57
     */
    public void createClassSchedule (CourseInfo courseInfo) {

        // 读取每个课表数据中的开始时间和结束时间
        CourseStartAndEndTimeVo period = getClassTime(courseInfo.getCoursePeriod());
        CourseStartAndEndTimeVo section = getClassTime(courseInfo.getCourseSection());

        // 读取每个课表数据中的星期
        int week = Integer.parseInt(courseInfo.getCourseWeek());

        // 以周期开始第一层循环
        period:
        for (int i = period.getStartTime(); i <= period.getEndTime(); i++) {

            // 以节次开始第二层循环
            for (int p = section.getStartTime(); p <= section.getEndTime(); p++) {
                // 检测课表时间是否有冲突
                if (!isNull(schedule[i][week][p])) {
                    System.out.println("警告：课程表中有课程时间冲突，请检查");
                    System.out.println("冲突课程ID：" + courseInfo.getCourseId() + ";冲突课程ID：" + schedule[i][week][p].getCourseId());
                    // 直接跳出周期循环
                    break period;
                }
                // 将对应周期、星期、节次的课程数据写入数组
                schedule[i][week][p] = courseInfo;
            }

        }

    }

    /**
     * 通过split方法分割开始时间与结束时间
     *
     * @param classStringTime 上课时间数据
     *
     * @return 返回两个int型的数据作为开始时间与结束时间
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:57
     */
    public CourseStartAndEndTimeVo getClassTime (String classStringTime) {

        // 开始时间与结束时间实体类
        CourseStartAndEndTimeVo courseStartAndEndTimeVo = new CourseStartAndEndTimeVo();

        // 判断是否属于时间段
        if (classStringTime.contains("-")) {

            // 以 "-" 符号分割开始时间与结束时间
            String[] temp = classStringTime.split("-");

            // 分别设置开始时间与结束时间
            courseStartAndEndTimeVo.setStartTime(Integer.parseInt(temp[0]));
            courseStartAndEndTimeVo.setEndTime((Integer.parseInt(temp[1])));

            // 返回封装好的开始时间与结束时间
            return courseStartAndEndTimeVo;

        }

        // 直接返回数据，并将其同时作为开始时间与结束时间
        courseStartAndEndTimeVo.setStartTime(Integer.parseInt(classStringTime));
        courseStartAndEndTimeVo.setEndTime(Integer.parseInt(classStringTime));

        return courseStartAndEndTimeVo;

    }

    /**
     * 封装五大节课程数据
     *
     * @param period   当前周期
     * @param pushTime 推送时间
     *
     * @return 返回封装好的五大节课程数据
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:57
     */
    public CourseSectionVo getCourse (int period, String pushTime) {

        // 根据当前日期获取星期
        int week = (DateUtil.getW(new Date()) % 7);

        // 判断是否需要调试星期
        if (!enterpriseDataService.queryingEnterpriseData("debugWeek").isEmpty()) {

            week = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugWeek"));
            System.out.println("测试星期：" + week);

        } else {

            System.out.println("当前星期：" + week);

        }

        // 根据推送时间偏移星期
        week = (week + Integer.parseInt(pushTime)) % 7;

        // 判断是否是debug中，如是则不计算专业课程数
        if (enterpriseDataService.queryingEnterpriseData("departmentId").equals("1")) {
            // 根据courseInfo表中的totalSpecializedClassTimes字段判断今天是否有专业课程
            for (int i = 1; i < 5; i++) {
                // 非空判断
                if (schedule[period][week][i] != null) {
                    // 专业课程判断
                    if (schedule[period][week][i].getCourseSpecialized() == 1) {
                        // 获取已上课的专业课程次数
                        Integer totalSpecializedClassTimes = Integer.valueOf(enterpriseDataService.queryingEnterpriseData("totalSpecializedClassTimes"));
                        // 自增
                        totalSpecializedClassTimes++;
                        // 回写
                        enterpriseDataService.updateEnterpriseData("totalSpecializedClassTimes", String.valueOf(totalSpecializedClassTimes));
                    }
                }
            }
        }


        // 封装五大节课程数据并将其返回
        courseSectionVo.setFirst(schedule[period][week][1]);
        courseSectionVo.setSecond(schedule[period][week][2]);
        courseSectionVo.setThirdly(schedule[period][week][3]);
        courseSectionVo.setFourthly(schedule[period][week][4]);
        courseSectionVo.setFifth(schedule[period][week][5]);

        return courseSectionVo;

    }

    /**
     * 统计总课程数
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:58
     */
    public void extracted () {
        // 判断是否是debug中，如是则不计算课程数
        if (enterpriseDataService.queryingEnterpriseData("departmentId").equals("3")) {
            return;
        }
        // 获取当前总课程数
        int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("totalClassTimes"));
        // 自增
        temp++;
        // 回写
        enterpriseDataService.updateEnterpriseData("totalClassTimes", String.valueOf(temp));

    }

}
