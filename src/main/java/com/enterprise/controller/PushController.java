package com.enterprise.controller;

import com.enterprise.config.ScheduledConfig;
import com.enterprise.entity.CourseData;
import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ParameterListVo;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.service.impl.SendMessageServiceImpl;
import com.enterprise.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * 推送服务
 *
 * @author PrefersMin
 * @version 1.3
 */
@RestController
public class PushController {

    /**
     * 工具类
     */
    @Resource
    DateUtil dateUtil;
    @Resource
    CourseDataUtil courseDataUtil;

    @Resource
    CurriculumDataUtil curriculumDataUtil;

    @Resource
    PushDataUtil pushDataUtil;
    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    /**
     * 五大节课程实体类
     */
    List<CurriculumData> curriculumDataList;
    /**
     * 声明一个标题
     */
    String title;
    @Resource
    private SendMessageServiceImpl sendMessage;

    /**
     * 课程推送主方法
     *
     * @author PrefersMin
     */
    @GetMapping("/pushCourse")
    public void pushCourse() {

        // 推送前刷新一遍数据(天气播报位置、彩虹屁Api、开学日期、放假日期)
        ParameterListVo parameterList = pushDataUtil.getParameterList();

        // 微信小表情网站：https://www.emojiall.com/zh-hans/platform-wechat
        // 创建一个可变字符串类作为容器用以追加字符串到消息序列
        StringBuilder message = new StringBuilder();

        // 根据开学日期进行判断
        if (parameterList.getDateStarting() >= 0) {
            System.out.println("开学天数：" + parameterList.getDateStarting());

            // 计算当前推送周期、获取推送时间、计算当前推送星期
            int pushTime = pushDataUtil.getPushTime();
            int period = dateUtil.getPeriod();
            int week = dateUtil.getW();

            // 推送前刷新一遍课表
            // curriculumDataUtil.resetCurriculumData();

            // 根据周期与星期获取五大节课程数据
            curriculumDataList = curriculumDataUtil.getTodayCurriculumData(period, week);

            // 非空判断五大节课程数据(与逻辑)
            if (isNull(curriculumDataList)) {
                // 五大节课程数据都为空，跳过当天的推送
                System.out.println("当前没有课程，跳过推送");
                return;
            }

            // 假日信息推送，若距离假期天数为 0 触发
            if (parameterList.getDateEnding() == 0) {

                vacationPush(parameterList, message);
                return;
            }

            // 根据推送时间设置标题
            if (ScheduledConfig.NIGHT_PUSH_MODE == pushTime) {
                title = "\uD83C\uDF1F晚上好";
            } else {
                title = "\uD83C\uDF1E早上好~";
            }

            // 消息内容
            // 天气非空判断
            if (parameterList.getWeatherVo() != null) {
                // 根据推送时间判断天气推送提示
                message.append("\n\uD83D\uDCCD").append(parameterList.getWeatherVo().getArea()).append(ScheduledConfig.NIGHT_PUSH_MODE == pushTime ? "明日" : "今日").append("天气");
                message.append("\n\uD83C\uDF25气象：").append(parameterList.getWeatherVo().getDayWeather());
                message.append("\n\uD83C\uDF21温度：").append(parameterList.getWeatherVo().getNightTemp()).append("℃~").append(parameterList.getWeatherVo().getDayTemp()).append("℃\n");

            }

            // 距离开学日天数统计
            if (parameterList.getDateStarting() == 0) {
                message.append("\n\uD83C\uDF92今天是开学日噢~");
            } else {
                message.append("\n\uD83C\uDF92你已经上了").append(parameterList.getDateStarting()).append("天的课了~");
            }


            // 距离放假日天数判断
            if (parameterList.getDateEnding() == 1) {
                message.append("\n\n\uD83C\uDF92明天就放假咯~");
            } else {
                message.append("\n\uD83C\uDFC1距离放假还有").append(parameterList.getDateEnding() - pushTime).append("天");
            }

            // 彩虹屁非空判断
            if (StringUtils.isNotEmpty(parameterList.getCaiHongPi())) {
                message.append("\n\n\uD83D\uDC8C").append(parameterList.getCaiHongPi());
            }

            // 循环推送多个用户
            sendMessage.pushCourse(title, message.toString());

            // 清空内容
            title = "";
            message.setLength(0);

            // 根据推送时间设置标题
            if (ScheduledConfig.NIGHT_PUSH_MODE == pushTime) {
                title = "\uD83C\uDF08明天是";
            } else {
                title = "\uD83C\uDF08今天是";
            }

            title = title + parameterList.getWeatherVo().getDate() + " 第" + period + "周 " + dateUtil.getWeek(pushTime);
            // 设置课程信息并统计课程节数
            courseSet(message, curriculumDataList);

            sendMessage.pushCourse(title, message.toString());

        } else if (parameterList.getDateStarting() == -1) {
            startPush(parameterList, message);

        } else {
            System.out.println("开学日期" + parameterList.getDateStarting());
            System.out.println("没有开学，停止推送");
        }

    }


    /**
     * 五大节课程非空判断、统计早晚课天数与总课程数
     *
     * @author PrefersMin
     *
     * @param message 传入的message
     */
    private void courseSet(StringBuilder message, List<CurriculumData> curriculumDataList) {

        String[] selectionStringArray = {"\n1️⃣第一大节：", "\n2️⃣第二大节：", "\n3️⃣第三大节：", "\n4️⃣第四大节：", "\n5️第五大节："};

        curriculumDataList.forEach(curriculumData -> {
            int curriculumSection = curriculumData.getCurriculumSection() - 1;
            // 总课程数统计
            courseDataUtil.courseCount();
            message.append(selectionStringArray[curriculumSection]);
            message.append(curriculumData.getCourseName());
            message.append("\n\uD83D\uDE8F上课地点：").append(curriculumData.getCourseVenue()).append("\n");

            // 判断是否是debug中，如不是则计算早晚课天数
            if (!enterpriseDataService.queryingEnterpriseData("debugPushMode").getDataValue().equals(enterpriseDataService.queryingEnterpriseData("departmentId").getDataValue())) {
                if (curriculumSection == 0) {

                    // 早课天数统计
                    int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("morningClassDays").getDataValue());
                    temp++;
                    enterpriseDataService.updateEnterpriseDataByDataName("morningClassDays", String.valueOf(temp));

                } else if (curriculumSection == 4) {

                    // 晚课天数统计
                    int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("nightClassDays").getDataValue());
                    temp++;
                    enterpriseDataService.updateEnterpriseDataByDataName("nightClassDays", String.valueOf(temp));

                }
            }

        });
    }

    /**
     * 开学日推送
     *
     * @author PrefersMin
     *
     * @param parameterList 传入的参数列表
     * @param message 传入的message
     */
    private void startPush(ParameterListVo parameterList, StringBuilder message) {
        System.out.println("开学日期" + parameterList.getDateStarting());
        // 标题
        title = "\uD83C\uDF92明天是开学日噢，明晚开始推送课程信息~";

        // 获取所有课程信息，并提取其中的课程名称
        List<CourseData> courseData = courseDataUtil.queryAllCourseData();
        List<String> courseNames = courseData.stream().map(CourseData::getCourseName).collect(Collectors.toList());
        // 通过TreeSet对课程名称进行去重
        courseNames = new ArrayList<>(new TreeSet<>(courseNames));

        // 计算课程门数，并追加课程名称到消息序列
        message.append("\n\uD83D\uDCDA本学期共有").append(courseNames.size()).append("门课程\n\n");
        for (String courseName : courseNames) {
            message.append(courseName).append("\n\n");
        }

        // 提醒明天的天气与温度
        message.append("\n\uD83C\uDF25明天的气象：").append(parameterList.getWeatherVo().getDayWeather());
        message.append("\n\uD83C\uDF21温度：").append(parameterList.getWeatherVo().getNightTemp()).append("~").append(parameterList.getWeatherVo().getDayTemp()).append("\n");

        message.append("\n\uD83C\uDFC4\uD83C\uDFFB\u200D♀️新学期马上开始咯\n");

        // 循环推送多个用户
        sendMessage.pushCourse(title, message.toString());
    }

    /**
     * 假日推送
     *
     * @author PrefersMin
     *
     * @param parameterList 传入的参数列表
     * @param message 传入的message
     */
    private void vacationPush(ParameterListVo parameterList, StringBuilder message) {

        title = "\uD83C\uDFC1本学期的课程到此结束啦，一起来回顾一下吧";

        message.append("\n\uD83D\uDCD7这学期你一共上了 ").append(parameterList.getDateStarting()).append(" 天的课\n");
        message.append("\uD83C\uDF1E其中有 ").append(enterpriseDataService.queryingEnterpriseData("morningClassDays").getDataValue()).append(" 天的早课\n");
        message.append("\uD83C\uDF1B以及 ").append(enterpriseDataService.queryingEnterpriseData("nightClassDays").getDataValue()).append(" 天的晚课\n");
        message.append("✌早起的痛苦明天就远离你啦~\n");

        message.append("\n\uD83D\uDCD8在这学期你一共认真听讲了 ").append(enterpriseDataService.queryingEnterpriseData("totalClassTimes").getDataValue()).append(" 节的课程\n");
        message.append("\uD83C\uDF93其中有 ").append(enterpriseDataService.queryingEnterpriseData("totalSpecializedClassTimes").getDataValue()).append(" 节的专业课程\n");
        message.append("❓你的专业技能是否有所增长呢？\n");

        message.append("\n\uD83D\uDE0A本学期的工作到此结束，同学们下学期再见~\n");

        // 循环推送多个用户
        sendMessage.pushCourse(title, message.toString());

    }

    /**
     * 推送纯文本消息
     *
     * @author PrefersMin
     *
     * @param message 需要推送的消息内容
     */
    @GetMapping("/pushTextMsg")
    public void pushTextMsg(String message) {
        sendMessage.sendTextMsg(message);
    }

    /**
     * 推送图文消息
     *
     * @author PrefersMin
     *
     * @param title 需要推送的消息标题
     * @param message 需要推送的消息内容
     */
    @GetMapping("/pushConferenceMsg")
    public void pushConferenceMsg(String title, String message) {
        sendMessage.sendNewsMsg(title, message);
    }

}
