package com.enterprise.service;

import me.chanjar.weixin.cp.bean.message.WxCpMessageSendResult;

public interface SendMessageService {

    WxCpMessageSendResult pushCourse(String title, String message);

    WxCpMessageSendResult sendTextMsg(String message);

    void sendTextMsg(String message, String sendUser);

    WxCpMessageSendResult sendNewsMsg(String title, String message);

}
