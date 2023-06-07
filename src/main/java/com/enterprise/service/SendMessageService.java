package com.enterprise.service;

import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;

/**
 * 企业微信消息接口
 *
 * @author PrefersMin
 * @version 1.0
 */
public interface SendMessageService {

    WxCpMessageSendResult pushCourse(String title, String message);

    WxCpMessageSendResult sendTextMsg(String message);

    void sendTextMsg(String message, String sendUser);

    WxCpMessageSendResult sendNewsMsg(String title, String message);

}
