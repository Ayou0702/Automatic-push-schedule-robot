package com.enterprise.controller;

import com.enterprise.entity.CourseData;
import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.vo.ParameterListVo;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.service.SendMessageService;
import com.enterprise.util.*;
import com.enterprise.util.enums.PushMode;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 推送服务
 *
 * @author PrefersMin
 * @version 1.8
 */
@RestController
@RequiredArgsConstructor
public class PushController {

    /**
     * 封装返回结果
     */
    private final Result result;

    /**
     * 日期工具类
     */
    private final DateUtil dateUtil;

    /**
     * 推送数据的工具类
     */
    private final PushDataUtil pushDataUtil;

    /**
     * 课程数据工具类
     */
    private final CourseDataUtil courseDataUtil;

    /**
     * 线性课程表数据工具类
     */
    private final CurriculumDataUtil curriculumDataUtil;

    /**
     * 配置数据工具类
     */
    private final EnterpriseDataUtil enterpriseDataUtil;

    /**
     * 企业微信消息接口
     */
    private final SendMessageService sendMessageService;

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 课程数据接口
     */
    private final CourseDataService courseDataService;

    /**
     * 声明一个标题
     */
    private String title;

    /**
     * 微信服务返回结果
     */
    private WxCpMessageSendResult wxCpMessageSendResult;

    /**
     * 课程推送主方法
     *
     * @author PrefersMin
     */
    @GetMapping("/pushCourse")
    public ResultVo pushCourse() {

        // 推送前刷新一遍数据(天气播报位置、彩虹屁Api、开学日期、放假日期)
        ParameterListVo parameterList = pushDataUtil.getParameterList();

        // 微信小表情网站：https://www.emojiall.com/zh-hans/platform-wechat
        // 创建一个可变字符串类作为容器用以追加字符串到消息序列
        StringBuilder message = new StringBuilder();

        // 根据开学日期进行判断
        if (parameterList.getDateStarting() >= 0) {
            LogUtil.info("开学天数：" + parameterList.getDateStarting());

            // 计算当前推送周期、获取推送时间、计算当前推送星期
            int pushMode = pushDataUtil.getPushMode();
            int period = dateUtil.getPeriod();
            int week = dateUtil.getW();

            // 根据周期与星期获取五大节课程数据
            List<CurriculumData> curriculumDataList = curriculumDataUtil.getTodayCurriculumData(period, week);

            // 假日信息推送，若距离假期天数为 0 触发
            if (parameterList.getDateEnding() == 0) {
                return vacationPush(parameterList, message);
            }

            // 非空判断五大节课程数据(与逻辑)
            if (curriculumDataList.isEmpty()) {
                // 五大节课程数据都为空，跳过当天的推送
                LogUtil.info("当前没有课程，跳过推送");
                return result.success("当前没有课程，跳过推送");
            }

            // 根据推送时间设置标题
            if (PushMode.NIGHT.getValue() == pushMode) {
                title = "\uD83C\uDF1F晚上好";
            } else {
                title = "\uD83C\uDF1E早上好~";
            }

            // 消息内容
            // 根据推送时间判断天气推送提示
            message.append("\n\uD83D\uDCCD").append(parameterList.getWeatherVo().getArea()).append(PushMode.NIGHT.getValue() == pushMode ? "明日" : "今日").append("天气");
            message.append("\n\uD83C\uDF25气象：").append(parameterList.getWeatherVo().getWeather());
            message.append("\n\uD83C\uDF21温度：").append(parameterList.getWeatherVo().getLowest()).append("℃~").append(parameterList.getWeatherVo().getHighest()).append("℃\n");

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
                message.append("\n\uD83C\uDFC1距离放假还有").append(parameterList.getDateEnding() - pushMode).append("天");
            }

            // 彩虹屁非空判断
            if (StringUtils.isNotEmpty(parameterList.getCaiHongPi())) {
                message.append("\n\n\uD83D\uDC8C").append(parameterList.getCaiHongPi());
            }

            // 循环推送多个用户
            try {
                wxCpMessageSendResult = pushCourse(title, message);
            } catch (Exception e) {
                return result.failed(e.getMessage());
            }

            // 清空内容
            title = "";
            message.setLength(0);

            // 根据推送时间设置标题
            if (PushMode.NIGHT.getValue() == pushMode) {
                title = "\uD83C\uDF08明天是";
            } else {
                title = "\uD83C\uDF08今天是";
            }

            title = title + parameterList.getWeatherVo().getDate() + " 第" + period + "周 " + dateUtil.getWeek(pushMode);
            // 设置课程信息并统计课程节数
            courseSet(message, curriculumDataList);

            try {
                wxCpMessageSendResult = pushCourse(title, message);
            } catch (Exception e) {
                return result.failed(e.getMessage());
            }

            enterpriseDataUtil.dataIncrement("classDays");

            return result.success("课程推送成功", wxCpMessageSendResult);

        } else if (parameterList.getDateStarting() == -1) {
            return startPush(parameterList, message);
        } else {
            LogUtil.info("开学日期" + parameterList.getDateStarting());
            return result.success("没有开学，停止推送");
        }

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
        wxCpMessageSendResult = sendMessageService.sendTextMsg(message);
        logMessageSendResult();
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
        wxCpMessageSendResult = sendMessageService.sendNewsMsg(title, message);
        logMessageSendResult();
    }

    /**
     * 打印推送日志
     *
     * @author PrefersMin
     *
     */
    private void logMessageSendResult() {

        if (wxCpMessageSendResult.getErrCode() != 0) {
            LogUtil.error(wxCpMessageSendResult.toString());
        } else {
            LogUtil.info(wxCpMessageSendResult.toString());
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
                    enterpriseDataUtil.dataIncrement("morningClassDays");

                } else if (curriculumSection == 4) {

                    // 晚课天数统计
                    enterpriseDataUtil.dataIncrement("nightClassDays");

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
    private ResultVo startPush(ParameterListVo parameterList, StringBuilder message) {
        LogUtil.info("开学日期" + parameterList.getDateStarting());
        // 标题
        title = "\uD83C\uDF92明天是开学日噢，明晚开始推送课程信息~";

        // 获取所有课程信息，并提取其中的课程名称
        List<CourseData> courseData = courseDataService.queryAllCourseData();
        List<String> courseNames = courseData.stream().map(CourseData::getCourseName).collect(Collectors.toList());
        // 通过TreeSet对课程名称进行去重
        courseNames = new ArrayList<>(new TreeSet<>(courseNames));

        // 计算课程门数，并追加课程名称到消息序列
        message.append("\n\uD83D\uDCDA本学期共有").append(courseNames.size()).append("门课程\n\n");
        for (String courseName : courseNames) {
            message.append(courseName).append("\n\n");
        }

        // 提醒明天的天气与温度
        message.append("\n\uD83C\uDF25明天的气象：").append(parameterList.getWeatherVo().getWeather());
        message.append("\n\uD83C\uDF21温度：").append(parameterList.getWeatherVo().getLowest()).append("~").append(parameterList.getWeatherVo().getHighest()).append("\n");

        message.append("\n\uD83C\uDFC4\uD83C\uDFFB\u200D♀️新学期马上开始咯\n");

        try {
            pushCourse(title, message);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        return result.success("开学日推送成功");
    }

    /**
     * 推送课程
     *
     * @author PrefersMin
     *
     * @param title 推送标题
     * @param message 推送消息
     * @return 返回推送结果
     */
    private WxCpMessageSendResult pushCourse(String title, StringBuilder message) {

        // 循环推送多个用户
        wxCpMessageSendResult = sendMessageService.pushCourse(title, message.toString());

        logMessageSendResult();

        if (wxCpMessageSendResult.getErrCode() != 0) {
            sendMessageService.sendTextMsg("课程推送失败", enterpriseDataService.queryingEnterpriseData("debugUser").getDataValue());
        }

        return wxCpMessageSendResult;

    }

    /**
     * 假日推送
     *
     * @author PrefersMin
     *
     * @param parameterList 传入的参数列表
     * @param message 传入的message
     */
    private ResultVo vacationPush(ParameterListVo parameterList, StringBuilder message) {

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
        try {
            pushCourse(title, message);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        return result.success("假日推送成功");
    }

}
