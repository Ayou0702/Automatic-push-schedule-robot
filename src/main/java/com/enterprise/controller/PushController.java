package com.enterprise.controller;

import com.enterprise.entity.CourseInfo;
import com.enterprise.entity.vo.CourseSectionVo;
import com.enterprise.entity.vo.ParameterListVo;
import com.enterprise.service.EnterpriseDataServiceImpl;
import com.enterprise.service.SendMessageImpl;
import com.enterprise.util.CourseInfoUtil;
import com.enterprise.util.DateUtil;
import com.enterprise.util.PushDataUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @author ayou 2023/2/16
 */
@RestController
public class PushController {

    /**
     * 工具类
     */
    @Resource
    DateUtil dateUtil;
    @Resource
    CourseInfoUtil courseInfoUtil;
    @Resource
    PushDataUtil pushDataUtil;
    @Resource
    EnterpriseDataServiceImpl enterpriseDataService;
    // 五大节课程实体类
    CourseSectionVo courseSectionVo = new CourseSectionVo();
    // 声明一个标题
    String title;
    @Resource
    private SendMessageImpl sendMessage;

    /**
     * 课程推送主方法
     */
    @GetMapping("/pushCourse")
    public void pushCourse () {

        // 推送前刷新一遍数据(天气播报位置、彩虹屁Api、开学日期、放假日期)
        ParameterListVo parameterList = pushDataUtil.getParameterList();

        // 微信小表情网站：https://www.emojiall.com/zh-hans/platform-wechat
        // 创建一个可变字符串类作为容器用以追加字符串到消息序列
        StringBuilder message = new StringBuilder();

        // 根据开学日期进行判断
        if (parameterList.getDateStarting() >= 0) {
            System.out.println("开学日期" + parameterList.getDateStarting());
            // 计算当前推送周期、获取推送时间、计算当前推送星期
            int period = dateUtil.getPeriod();
            String pushTime = parameterList.getPushTime();
            String week = dateUtil.getWeek(pushTime);

            // 推送前刷新一遍课表
            courseInfoUtil.updateCourseInfo();

            // 根据周期与星期获取五大节课程数据
            courseSectionVo = courseInfoUtil.getCourse(period, pushTime);

            // 非空判断五大节课程数据(与逻辑)
            if (isNull(courseSectionVo.getFirst()) && isNull(courseSectionVo.getSecond()) && isNull(courseSectionVo.getThirdly()) && isNull(courseSectionVo.getFourthly()) && isNull(courseSectionVo.getFifth())) {
                // 五大节课程数据都为空，跳过当天的推送
                System.out.println("当前没有课程，跳过推送");
                return;
            }

            // 假日信息推送，若距离假期天数为 0 触发
            if (parameterList.getDateEnding() == 0) {

                title = "\uD83C\uDFC1本学期的课程到此结束啦，一起来回顾一下吧";

                message.append("\n\uD83D\uDCD7这学期你一共上了 ").append(parameterList.getDateStarting()).append(" 天的课\n");
                message.append("\uD83C\uDF1E其中有 ").append(enterpriseDataService.queryingEnterpriseData("morningClassDays")).append(" 天的早课\n");
                message.append("\uD83C\uDF1B以及 ").append(enterpriseDataService.queryingEnterpriseData("nightClassDays")).append(" 天的晚课\n");
                message.append("✌早起的痛苦明天就远离你啦~\n");

                message.append("\n\uD83D\uDCD8在这学期你一共认真听讲了 ").append(enterpriseDataService.queryingEnterpriseData("totalClassTimes")).append(" 节的课程\n");
                message.append("\uD83C\uDF93其中有 ").append(enterpriseDataService.queryingEnterpriseData("totalSpecializedClassTimes")).append(" 节的专业课程\n");
                message.append("❓你的专业技能是否有所增长呢？\n");

                message.append("\n\uD83D\uDE0A本学期的工作到此结束，同学们下学期再见~\n");

                // 循环推送多个用户
                sendMessage.pushCourse(title, message.toString());
                return;
            }

            // 根据推送时间设置标题
            if (pushTime.equals("1")) {
                title = "\uD83C\uDF08晚上好~明天是本学期的 第" + (period + 1) + "周 " + week;
            } else {
                title = "\uD83C\uDF08早上好~今天是 " + parameterList.getWeatherVo().getDate() + " " + week;
            }

            // 消息内容
            // 天气非空判断
            if (parameterList.getWeatherVo() != null) {
                // 根据推送时间判断天气推送提示
                message.append("\n\uD83D\uDCCD").append(parameterList.getWeatherVo().getArea()).append(parameterList.getPushTime().equals("1") ? "明日" : "今日").append("天气");
                message.append("\n\uD83C\uDF25气象：").append(parameterList.getWeatherVo().getWeather());
                message.append("\n\uD83C\uDF21温度：").append(parameterList.getWeatherVo().getLowest()).append("~").append(parameterList.getWeatherVo().getHighest()).append("\n");

            }

            // 五大节课程非空判断、统计早晚课天数与总课程数
            if (!isNull(courseSectionVo.getFirst())) {
                // 总课程数统计
                courseInfoUtil.extracted();
                message.append("\n1️⃣第一大节：").append(courseSectionVo.getFirst().getCourseName());
                message.append("\n\uD83D\uDE8F上课地点：").append(courseSectionVo.getFirst().getCourseVenue()).append("\n");

                // 判断是否是debug中，如是则不计算早课天数
                if (enterpriseDataService.queryingEnterpriseData("departmentId").equals("1")) {
                    // 早课天数统计
                    int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("morningClassDays"));
                    temp++;
                    enterpriseDataService.updateEnterpriseData("morningClassDays", String.valueOf(temp));
                }

            }
            if (!isNull(courseSectionVo.getSecond())) {
                courseInfoUtil.extracted();
                message.append("\n2️⃣第二大节：").append(courseSectionVo.getSecond().getCourseName());
                message.append("\n\uD83D\uDE8F上课地点：").append(courseSectionVo.getSecond().getCourseVenue()).append("\n");
            }
            if (!isNull(courseSectionVo.getThirdly())) {
                courseInfoUtil.extracted();
                message.append("\n3️⃣第三大节：").append(courseSectionVo.getThirdly().getCourseName());
                message.append("\n\uD83D\uDE8F上课地点：").append(courseSectionVo.getThirdly().getCourseVenue()).append("\n");
            }
            if (!isNull(courseSectionVo.getFourthly())) {
                courseInfoUtil.extracted();
                message.append("\n4️⃣第四大节：").append(courseSectionVo.getFourthly().getCourseName());
                message.append("\n\uD83D\uDE8F上课地点：").append(courseSectionVo.getFourthly().getCourseVenue()).append("\n");
            }
            if (!isNull(courseSectionVo.getFifth())) {
                courseInfoUtil.extracted();
                message.append("\n5️第五大节：").append(courseSectionVo.getFifth().getCourseName());
                message.append("\n\uD83D\uDE8F上课地点：").append(courseSectionVo.getFifth().getCourseVenue()).append("\n");

                // 判断是否是debug中，如是则不计算晚课天数
                if (enterpriseDataService.queryingEnterpriseData("departmentId").equals("1")) {
                    // 晚课天数统计
                    int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("nightClassDays"));
                    temp++;
                    enterpriseDataService.updateEnterpriseData("nightClassDays", String.valueOf(temp));
                }

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
                message.append("\n\uD83C\uDFC1距离放假还有").append(parameterList.getDateEnding()).append("天");
            }

            // 彩虹屁非空判断
            if (StringUtils.isNotEmpty(parameterList.getCaiHongPi())) {
                message.append("\n\n\uD83D\uDC8C").append(parameterList.getCaiHongPi());
            }

            // 循环推送多个用户
            sendMessage.pushCourse(title, message.toString());

        } else if (parameterList.getDateStarting() == -1) {
            System.out.println("开学日期" + parameterList.getDateStarting());
            // 标题
            title = "\uD83C\uDF92明天是开学日噢，明晚开始推送课程信息~";

            // 获取所有课程信息，并提取其中的课程名称
            List<CourseInfo> courseInfos = courseInfoUtil.getCourseInfos();
            List<String> courseNames = courseInfos.stream().map(CourseInfo::getCourseName).collect(Collectors.toList());
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

            // 循环推送多个用户
            sendMessage.pushCourse(title, message.toString());

        } else {
            System.out.println("开学日期" + parameterList.getDateStarting());
            System.out.println("没有开学，停止推送");
        }

    }

    /**
     * 推送纯文本消息
     */
    @GetMapping("/pushTextMsg")
    public void pushTextMsg (String message) {
        sendMessage.sendTextMsg(message);
    }

    /**
     * 推送图文消息
     */
    @GetMapping("/pushConferenceMsg")
    public void pushConferenceMsg (String title, String message) {
        sendMessage.sendNewsMsg(title, message);
    }

}
