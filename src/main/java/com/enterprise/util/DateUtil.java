package com.enterprise.util;

import com.enterprise.service.EnterpriseDataService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

/**
 * 日期工具类
 *
 * @author PrefersMin
 * @version 1.5
 */
@Component
public class DateUtil {

    /**
     * 格式化
     */
    final static ThreadLocal<SimpleDateFormat> LOCAL_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /**
     * 推送数据的工具类
     */
    final PushDataUtil pushDataUtil;

    /**
     * 配置数据接口
     */
    final EnterpriseDataService enterpriseDataService;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param pushDataUtil 推送数据的工具类
     * @param enterpriseDataService 配置数据接口
     */
    public DateUtil(PushDataUtil pushDataUtil, EnterpriseDataService enterpriseDataService) {
        this.pushDataUtil = pushDataUtil;
        this.enterpriseDataService = enterpriseDataService;
    }

    /**
     * 计算两个日期(String类型)之间相差多少天
     *
     * @author PrefersMin
     *
     * @param startDateString 开始日期(String类型)
     * @param endDateString 结束日期(String类型)
     * @return 返回相差的天数(String类型)
     */
    public static int daysBetween(String startDateString, String endDateString) {
        long nd = 1000 * 24 * 60 * 60;

        // 转换时间格式
        Date startDate, endDate;
        try {
            startDate = LOCAL_FORMAT.get().parse(startDateString);
            endDate = LOCAL_FORMAT.get().parse(endDateString);
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
     * @author PrefersMin
     *
     * @param startDate 开始日期(String类型)
     * @param endDate 结束日期(String类型)
     * @return 返回相差的天数(long类型)
     */
    public static long getDiff(Date startDate, Date endDate) {
        return (endDate.getTime()) - (startDate.getTime());
    }

    /**
     * 获取当前日期(String类型)
     *
     * @author PrefersMin
     *
     * @return 当前日期(String类型)
     */
    static String getNow() {
        Date now = new Date(System.currentTimeMillis());
        return LOCAL_FORMAT.get().format(now);
    }

    /**
     * 获取当前的星期
     *
     * @author PrefersMin
     *
     * @return 当前的星期
     */
    public int getW() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        w = w % 7;

        // 判断是否需要调试星期
        if (!enterpriseDataService.queryingEnterpriseData("debugWeek").getDataValue().isEmpty()) {
            w = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugWeek").getDataValue());
            LogUtil.info("测试星期：" + w);
        } else {
            LogUtil.info("当前星期：" + w);
        }

        // 根据推送时间偏移星期
        w = (w + pushDataUtil.getPushTime()) % 8;

        return w;
    }

    /**
     * 获取当前周数(int类型)
     *
     * @author PrefersMin
     *
     * @return 当前周数(int类型)
     */
    public int getPeriod() {

        int period, periods;
        String date = LOCAL_FORMAT.get().format(new Date());

        periods = DateUtil.daysBetween(enterpriseDataService.queryingEnterpriseData("dateStarting").getDataValue(), date);

        period = ((periods + pushDataUtil.getPushTime()) / 7) + 1;

        // 调试，用于指定周数与当前星期
        if (!enterpriseDataService.queryingEnterpriseData("debugPeriod").getDataValue().isEmpty()) {
            period = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugPeriod").getDataValue());
            LogUtil.info("测试周数：" + period);
        } else {
            LogUtil.info("当前周数：" + period);
        }

        // 周数校验
        if (abs(period) > PushDataUtil.PERIOD_MAX) {
            period = 0;
            LogUtil.error("错误：周数超过课表最大周数限制，将会引发数组越界错误，已归零");
        } else if (period < 0) {
            period = abs(period);
            LogUtil.error("错误：周数为负数将会引发数组越界错误，已取绝对值");
        }

        return period;

    }

    /**
     * 根据日期判断当前星期
     *
     * @author PrefersMin
     *
     * @param pushTime 当前日期
     * @return 返回当前星期
     */
    public String getWeek(int pushTime) {

        String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if (!enterpriseDataService.queryingEnterpriseData("debugWeek").getDataValue().isEmpty()) {
            return week[(Integer.parseInt(enterpriseDataService.queryingEnterpriseData("debugWeek").getDataValue()) + pushTime) % 7];
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1 + pushTime;
            if (w < 0) {
                w = 0;
            }
            w = w % 7;
            return week[w];
        }

    }

}
