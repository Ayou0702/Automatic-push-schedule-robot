package com.enterprise.util;

import com.enterprise.service.EnterpriseDataServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

/**
 * 日期工具类
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:58
 */
@Component
public class DateUtil {

    // 格式化
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // 工具类
    @Resource
    private EnterpriseDataServiceImpl enterpriseDataService;

    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date   Date对象
     * @param format v
     *
     * @return Date对象的字符串表达形式
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:58
     */
    public static String formatDate (Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取当前的星期
     *
     * @param dt 当前的日期
     *
     * @return 当前的星期
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:58
     */
    public static int getW (Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    /**
     * 计算两个日期(String类型)之间相差多少天
     *
     * @param startDateString 开始日期(String类型)
     * @param endDateString   结束日期(String类型)
     *
     * @return 返回相差的天数(String类型)
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:58
     */
    public static int daysBetween (String startDateString, String endDateString) {
        long nd = 1000 * 24 * 60 * 60;

        // 转换时间格式
        Date startDate, endDate;
        try {
            startDate = simpleDateFormat.parse(startDateString);
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 计算差多少天
        long diff = getDiff(startDate, endDate);

        return (int) (diff / nd);
    }

    /**
     * 计算两个日期(Date类型)之间相差多少天
     *
     * @param startDate 开始日期(String类型)
     * @param endDate   结束日期(String类型)
     *
     * @return 返回相差的天数(long类型)
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:59
     */
    public static long getDiff (Date startDate, Date endDate) {
        return (endDate.getTime()) - (startDate.getTime());
    }

    /**
     * 获取当前日期(String类型)
     *
     * @return 当前日期(String类型)
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:59
     */
    static String getNow () {
        Date now = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(now);
    }

    /**
     * 获取当前周数(int类型)
     *
     * @return 当前周数(int类型)
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:59
     */
    public int getPeriod (int pushTime) {
        int periods;
        String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");

        periods = DateUtil.daysBetween(enterpriseDataService.queryingEnterpriseData("dateStarting"), date);

        int period = (periods + pushTime) / 7 + 1;


        // 调试，用于指定周数与当前星期
        if (!enterpriseDataService.queryingEnterpriseData("debugPeriod").isEmpty()) {
            period = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugPeriod"));
            System.out.println("测试周数：" + period);
        } else {
            System.out.println("当前周数：" + period);
        }

        // 周数校验
        if (abs(period) > 23) {
            period = 0;
            System.out.println("错误：周数超过课表最大周数限制，将会引发数组越界错误，已归零");
        } else if (period < 0) {
            period = abs(period);
            System.out.println("错误：周数为负数将会引发数组越界错误，已取绝对值");
        }

        return period;
    }

    /**
     * 根据日期判断当前星期
     *
     * @param pushTime 当前日期
     *
     * @return 返回当前星期
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:59
     */
    public String getWeek (String pushTime) {

        String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if (!enterpriseDataService.queryingEnterpriseData("debugWeek").isEmpty()) {
            return week[(Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugWeek")) + Integer.parseInt(pushTime)) % 7];
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1 + Integer.parseInt(pushTime);
            if (w < 0) w = 0;
            return week[w];
        }

    }

}
