package com.enterprise.service;

import com.enterprise.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 快速构建并发送消息类
 *
 * @author Iwlthxcl
 * @version 1.1
 */
@Service
@Slf4j
public class SendMessageService {

    /**
     * 工具类
     */
    @Resource
    WxCoreService wxCoreService;
    @Resource
    ApiUtil apiUtil;

    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    /**
     * 用于构建并发送课程相关消息
     *
     * @author Iwlthxcl
     *
     * @param title 推送的标题
     * @param message 推送的消息
     */
    public void pushCourse(String title, String message) {

        try {
            // 微信消息对象
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCoreService.getWxCpService());
            WxCpMessage pushCourse = new WxCpMessage();
            pushCourse.setSafe("0");
            // 设置消息类型
            pushCourse.setMsgType("textcard");
            // 设置发送用户
            pushCourse.setToUser(apiUtil.getParticipants());
            // 发送的标题
            pushCourse.setTitle(title);
            // 发送内容
            pushCourse.setDescription(message);
            // 设置跳转；可以自己制作一个网页
            pushCourse.setUrl(enterpriseDataService.queryingEnterpriseData("url"));
            pushCourse.setBtnTxt("PrefersMin");
            wxCpMessageService.send(pushCourse);
            log.info(pushCourse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 用于发送纯文本消息
     *
     * @author Iwlthxcl
     *
     * @param message 推送的消息
     */
    public void sendTextMsg(String message) {

        try {
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCoreService.getWxCpService());
            WxCpMessage textMsg = new WxCpMessage();
            textMsg.setSafe("0");
            // 设置消息类型
            textMsg.setMsgType("text");
            // 设置发送用户
            textMsg.setToUser(apiUtil.getParticipants());
            textMsg.setContent(message);
            wxCpMessageService.send(textMsg);
            log.info(textMsg.toString());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 用于发送图文消息
     *
     * @author Iwlthxcl
     *
     * @param title 推送的标题
     * @param message 推送的消息
     */
    public void sendNewsMsg(String title, String message) {

        try {
            // 微信消息对象
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCoreService.getWxCpService());
            WxCpMessage newsMsg = new WxCpMessage();
            newsMsg.setSafe("0");
            // 设置消息类型
            newsMsg.setMsgType("news");
            // 设置发送用户
            newsMsg.setToUser(apiUtil.getParticipants());

            List<NewArticle> articlesList = new ArrayList<>();
            NewArticle newArticle = new NewArticle();
            // 发送的标题
            newArticle.setTitle(title);
            // 按钮文本
            newArticle.setBtnText("PrefersMin");
            // 发送内容
            newArticle.setDescription(message);
            // 图片地址
            newArticle.setPicUrl(enterpriseDataService.queryingEnterpriseData("imgUrl"));
            // 设置跳转；可以自己制作一个网页
            newArticle.setUrl(enterpriseDataService.queryingEnterpriseData("url"));
            // 添加到List集合
            articlesList.add(newArticle);
            newsMsg.setArticles(articlesList);

            wxCpMessageService.send(newsMsg);

            log.info(newsMsg.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
