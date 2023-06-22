package com.enterprise.util;

import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.entity.vo.ScheduleInfo;
import com.enterprise.service.*;
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
 * @version 1.3
 */
@Component
@RequiredArgsConstructor
public class CurriculumDataUtil {

    /**
     * 封装返回结果
     */
    private final Result result;

    /**
     * 课表数据
     */
    private ScheduleInfo[][][] scheduleDataArray;

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
     * 多表联动接口
     */
    private final MultilistService multilistService;

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
    public ResultVo resetCurriculumData() {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            boolean isEmpty = true;

            while (isEmpty) {
                isEmpty = curriculumDataService.deleteAllCurriculumData();
            }

            List<ScheduleInfo> scheduleDataList = multilistService.resetCurriculumData();

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

            // 遍历存放着课表数据的三维数组
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
            return result.success("重置线性课程表成功");

        } catch (Exception e) {
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.failed("重置线性课程表失败");
        }

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
