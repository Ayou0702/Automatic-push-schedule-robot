package com.enterprise.service.impl;

import com.enterprise.service.EnterpriseDataService;
import com.enterprise.service.SendMessageService;
import com.enterprise.service.WxCoreService;
import com.enterprise.util.ApiUtil;
import com.enterprise.util.LogUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业微信消息接口实现类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    /**
     * 企业微信核心服务
     */
    final WxCoreService wxCoreService;

    /**
     * api工具类
     */
    final ApiUtil apiUtil;

    /**
     * 配置数据接口
     */
    final EnterpriseDataService enterpriseDataService;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param wxCoreService 企业微信核心服务
     * @param apiUtil api工具类
     * @param enterpriseDataService 配置数据接口
     */
    public SendMessageServiceImpl(WxCoreService wxCoreService, ApiUtil apiUtil, EnterpriseDataService enterpriseDataService) {
        this.wxCoreService = wxCoreService;
        this.apiUtil = apiUtil;
        this.enterpriseDataService = enterpriseDataService;
    }

    /**
     * 用于构建并发送课程相关消息
     *
     * @author PrefersMin
     *
     * @param title 推送的标题
     * @param message 推送的消息
     */
    public WxCpMessageSendResult pushCourse(String title, String message) {

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
            pushCourse.setUrl(enterpriseDataService.queryingEnterpriseData("url").getDataValue());
            pushCourse.setBtnTxt("PrefersMin");
            LogUtil.info(pushCourse.toString());

            return wxCpMessageService.send(pushCourse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 用于发送纯文本消息
     *
     * @author PrefersMin
     *
     * @param message 推送的消息
     */
    public WxCpMessageSendResult sendTextMsg(String message) {

        try {
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCoreService.getWxCpService());
            WxCpMessage textMsg = new WxCpMessage();
            textMsg.setSafe("0");
            // 设置消息类型
            textMsg.setMsgType("text");
            // 设置发送用户
            textMsg.setToUser(apiUtil.getParticipants());
            textMsg.setContent(message);
            LogUtil.info(textMsg.toString());
            return wxCpMessageService.send(textMsg);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 用于发送纯文本消息
     *
     * @param message 推送的消息
     *
     * @author PrefersMin
     */
    public void sendTextMsg(String message, String sendUser) {

        try {
            WxCpMessageServiceImpl wxCpMessageService = new WxCpMessageServiceImpl(wxCoreService.getWxCpService());
            WxCpMessage textMsg = new WxCpMessage();
            textMsg.setSafe("0");
            // 设置消息类型
            textMsg.setMsgType("text");
            // 设置发送用户
            textMsg.setToUser(sendUser);
            textMsg.setContent(message);
            LogUtil.info(textMsg.toString());

            wxCpMessageService.send(textMsg);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 用于发送图文消息
     *
     * @author PrefersMin
     *
     * @param title 推送的标题
     * @param message 推送的消息
     */
    public WxCpMessageSendResult sendNewsMsg(String title, String message) {

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
            newArticle.setPicUrl(enterpriseDataService.queryingEnterpriseData("imgUrl").getDataValue());
            // 设置跳转；可以自己制作一个网页
            newArticle.setUrl(enterpriseDataService.queryingEnterpriseData("url").getDataValue());
            // 添加到List集合
            articlesList.add(newArticle);
            newsMsg.setArticles(articlesList);
            LogUtil.info(newsMsg.toString());

            return wxCpMessageService.send(newsMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
