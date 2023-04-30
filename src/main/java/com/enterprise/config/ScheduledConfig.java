package com.enterprise.config;

import com.enterprise.controller.PushController;
import com.enterprise.service.EnterpriseDataService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时调用类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Configuration
public class ScheduledConfig {

    /**
     * 日夜推送模式
     */
    public static final int NIGHT_PUSH_MODE = 1;
    public static final int DAY_PUSH_MODE = 0;

    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    @Resource
    private PushController pushController;

    /**
     * 每天的22:30触发推送
     */
    @Scheduled(cron = "0 30 22 ? * *")
    public void scheduledPushCourseNight() {
        if (NIGHT_PUSH_MODE == Integer.parseInt(enterpriseDataService.queryingEnterpriseData("pushTime").getDataValue())) {
            pushController.pushCourse();
        }
    }

    /**
     * 每天的7:45触发推送
     */
    @Scheduled(cron = "0 45 7 ? * *")
    public void scheduledPushCourseDay() {
        if (DAY_PUSH_MODE == Integer.parseInt(enterpriseDataService.queryingEnterpriseData("pushTime").getDataValue())) {
            pushController.pushCourse();
        }
    }

    /*
      cron值可以是
      "/5 * ?"                每隔5秒执行一次
      "0 /1 ?"                 "每隔1分钟执行一次
      "0 0 10,14,16 ?"        每天上午10点，下午2点，4点
      "0 0/30 9-17 ?"         朝九晚五工作时间内每半小时
      "0 0 12 ? * WED"        表示每个星期三中午12点
      “0 0 12 ?”              每天中午12点触发
      “0 15 10 ? “            每天上午10:15触发
      “0 15 10 ?”             每天上午10:15触发
      “0 15 10 ? *”           每天上午10:15触发
      “0 15 10 ? 2005”        2005年的每天上午10:15触发
      “0 14 * ?”              在每天下午2点到下午2:59期间的每1分钟触发
      “0 0/5 14 ?”            在每天下午2点到下午2:55期间的每5分钟触发
      “0 0/5 14,18 ?”         在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
      “0 0-5 14 ?”            在每天下午2点到下午2:05期间的每1分钟触发
      “0 10,44 14 ? 3 WED”    每年三月的星期三的下午2:10和2:44触发
      “0 15 10 ? * MON-FRI”   周一至周五的上午10:15触发
      “0 15 10 15 * ?”        每月15日上午10:15触发
      “0 15 10 L * ?”         每月最后一日的上午10:15触发
      “0 15 10 ? * 6L”        每月的最后一个星期五上午10:15触发
      “0 15 10 ? * 6#3”       每月的第三个星期五上午10:15触发
      “0 15 10 ? * 6L 2002-2005” 2002年至2005年的每月的最后一个星期五上午10:15触发
     */

}
