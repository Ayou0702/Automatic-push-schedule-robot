package com.enterprise.util;

import com.enterprise.common.handler.Result;
import com.enterprise.service.data.entity.CurriculumDataService;
import com.enterprise.service.data.entity.EnterpriseDataService;
import com.enterprise.service.data.view.ScheduleInfoDataService;
import com.enterprise.vo.data.entity.CurriculumData;
import com.enterprise.vo.pojo.ScheduleInfoDataVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * 线性课程表数据工具类
 *
 * @author PrefersMin
 * @version 1.6
 */
@Component
@RequiredArgsConstructor
public class CurriculumDataUtil {

    /**
     * 课表数据
     */
    private ScheduleInfoDataVo[][][] scheduleDataArray;

    /**
     * 将课表数据填入线性课程表的临时存储
     */
    private final ArrayList<CurriculumData> curriculumDataArrayList = new ArrayList<>();

    /**
     * 线性课程表数据接口
     */
    private final CurriculumDataService curriculumDataService;

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 课表详细数据视图表接口
     */
    private final ScheduleInfoDataService scheduleInfoDataService;

    /**
     * 配置数据工具类
     */
    private final EnterpriseDataUtil enterpriseDataUtil;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 重置线性课程表数据
     *
     * @author PrefersMin
     *
     */
    public Result resetCurriculumData() {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            boolean isEmpty = true;

            while (isEmpty) {
                isEmpty = curriculumDataService.deleteAllCurriculumData();
            }

            List<ScheduleInfoDataVo> scheduleDataList = scheduleInfoDataService.getAllScheduleInfoData();

            scheduleDataArray = new ScheduleInfoDataVo[PushDataUtil.PERIOD_MAX][PushDataUtil.WEEK_MAX][PushDataUtil.SECTION_MAX];

            // 通过for循环将课表数据按照上课时间填入数组中
            scheduleDataList.forEach(scheduleData -> {

                // 读取每个课表数据中的开始时间和结束时间
                int[] periodArray = getClassTime(scheduleData.getSchedulePeriod());
                int[] sectionArray = getClassTime(scheduleData.getScheduleSection());

                // 读取每个课表数据中的星期
                int week = Integer.parseInt(scheduleData.getScheduleWeek());

                // 以周期开始第一层循环
                period:
                for (int period = periodArray[0]; period <= periodArray[1]; period++) {

                    // 以节次开始第二层循环
                    for (int section = sectionArray[0]; section <= sectionArray[1]; section++) {
                        // 检测课表时间是否有冲突
                        if (!isNull(scheduleDataArray[period][week][section])) {
                            LogUtil.warn("课程表中有课程时间冲突，请检查");
                            LogUtil.warn("冲突课程名称：" + scheduleData.getCourseName() + ";冲突课程名称：" + scheduleDataArray[period][week][section].getCourseName());
                            // 直接跳出周期循环
                            break period;
                        }
                        // 将对应周期、星期、节次的课程数据写入数组
                        scheduleDataArray[period][week][section] = scheduleData;
                    }

                }
            });

            // 遍历存放着课表数据的三维数组
            for (int period = 0; period < scheduleDataArray.length; period++) {
                for (int week = 0; week < scheduleDataArray[period].length; week++) {
                    for (int section = 0; section < scheduleDataArray[period][week].length; section++) {
                        if (!isNull(scheduleDataArray[period][week][section])) {

                            CurriculumData curriculumData = getCurriculumData(period, week, section);

                            // 存入临时变量
                            curriculumDataArrayList.add(curriculumData);

                        }
                    }
                }
            }

            // 写入至线性课程表
            curriculumDataArrayList.forEach(curriculumDataService::addCurriculumData);
            // 清空临时变量
            curriculumDataArrayList.clear();
            // 提交事务
            platformTransactionManager.commit(transactionStatus);
            return Result.success().message("重置线性课程表成功");

        } catch (Exception e) {
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return Result.failed().message("重置线性课程表失败");
        }

    }

    /**
     * 返回课程数据
     *
     * @param period 周数
     * @param week 星期
     * @param section 节次
     * @return 课程数据
     */
    private CurriculumData getCurriculumData(int period, int week, int section) {
        CurriculumData curriculumData = new CurriculumData();

        curriculumData.setCourseName(scheduleDataArray[period][week][section].getCourseName());
        curriculumData.setCourseVenue(scheduleDataArray[period][week][section].getCourseVenue());
        curriculumData.setCourseSpecialized(scheduleDataArray[period][week][section].isCourseSpecialized());
        curriculumData.setCourseId(scheduleDataArray[period][week][section].getCourseId());

        curriculumData.setTeacherName(scheduleDataArray[period][week][section].getTeacherName());
        curriculumData.setTeacherPhone(scheduleDataArray[period][week][section].getTeacherPhone());
        curriculumData.setTeacherInstitute(scheduleDataArray[period][week][section].getTeacherInstitute());
        curriculumData.setTeacherId(scheduleDataArray[period][week][section].getTeacherId());
        curriculumData.setTeacherSpecialized(scheduleDataArray[period][week][section].isCourseSpecialized());

        curriculumData.setCurriculumPeriod(period);
        curriculumData.setCurriculumWeek(week);
        curriculumData.setCurriculumSection(section);
        return curriculumData;
    }

    /**
     * 通过split方法分割开始时间与结束时间
     *
     * @author PrefersMin
     *
     * @param classStringTime 上课时间数据
     * @return 返回两个int型的数据作为开始时间与结束时间
     */
    public static int[] getClassTime(String classStringTime) {

        // 开始时间与结束时间实体类
        int[] time = new int[2];

        // 分割字符
        String splitString = "-";

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
                        enterpriseDataUtil.dataIncrement("totalSpecializedClassTimes");
                    }
                }
            }
        }

        return curriculumDataService.queryCurriculumDataByTime(period, week);

    }

}
