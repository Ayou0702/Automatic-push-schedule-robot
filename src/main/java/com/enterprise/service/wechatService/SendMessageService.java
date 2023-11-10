package com.enterprise.service.wechatService;

import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;

/**
 * 企业微信消息接口
 *
 * @author PrefersMin
 * @version 1.2
 */
public interface SendMessageService {

    /**
     * 用于构建并发送课程相关消息
     *
     * @author PrefersMin
     *
     * @param title 推送的标题
     * @param message 推送的消息
     * @return 返回推送结果
     */
    WxCpMessageSendResult pushCourse(String title, String message);

    /**
     * 用于发送纯文本消息
     *
     * @author PrefersMin
     *
     * @param message 推送的消息
     * @return 返回推送结果
     */
    WxCpMessageSendResult sendTextMsg(String message);

    /**
     * 用于发送纯文本消息
     *
     * @author PrefersMin
     *
     * @param message 推送的消息
     *
     * @param sendUser 推送的用户
     */
    void sendTextMsg(String message, String sendUser);

    /**
     * 用于发送图文消息
     *
     * @author PrefersMin
     *
     * @param title 推送的标题
     * @param message 推送的消息
     * @return 返回推送结果
     */
    WxCpMessageSendResult sendNewsMsg(String title, String message);

}
