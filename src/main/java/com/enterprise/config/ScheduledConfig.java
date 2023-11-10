package com.enterprise.config;

import com.enterprise.controller.PushController;
import com.enterprise.service.data.entity.EnterpriseDataService;
import com.enterprise.service.wechatService.SendMessageService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.enums.PushMode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时调用类
 *
 * @author PrefersMin
 * @version 1.6
 */
@Configuration
@RequiredArgsConstructor
public class ScheduledConfig {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 企业微信消息接口
     */
    private final SendMessageService sendMessageService;

    /**
     * 推送服务
     */
    private final PushController pushController;

    /**
     * 每天的7:45触发推送
     */
    @Scheduled(cron = "0 45 7 ? * *")
    public void scheduledPushCourseDay() {
        doPushIf(enterpriseDataService.queryingEnterpriseData("pushMode").getDataValue().equals(String.valueOf(PushMode.DAY.getValue())), "晨间推送");
    }

    /**
     * 每天的22:30触发推送
     */
    @Scheduled(cron = "0 30 22 ? * *")
    public void scheduledPushCourseNight() {
        doPushIf(enterpriseDataService.queryingEnterpriseData("pushMode").getDataValue().equals(String.valueOf(PushMode.NIGHT.getValue())), "夜间推送");
    }

    /**
     * 判断是否触发推送
     *
     * @author PrefersMin
     *
     * @param condition 是否触发推送
     * @param timeDesc 推送模式
     */
    private void doPushIf(boolean condition, String timeDesc) {
        if (condition) {
            int code = 0;
            try {
                code = pushController.pushCourse().getCode();
            } catch (Exception e) {
                sendMessageService.sendTextMsg("课程推送失败", enterpriseDataService.queryingEnterpriseData("debugUser").getDataValue());
            }
            if (code != 200) {
                LogUtil.error(timeDesc + "失败");
            }
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
