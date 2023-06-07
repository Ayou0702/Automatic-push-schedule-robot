package com.enterprise.service;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

/**
 * 企业微信核心服务
 *
 * @author PrefersMin
 * @version 1.0
 */
public interface WxCoreService {

    WxCpService getWxCpService();

    WxCpDefaultConfigImpl getWxCpDefaultConfig();

    void resetTokenAndJsApi(WxCpService wxCpService, WxCpDefaultConfigImpl wxCpDefaultConfig);

    String getAccessToken();

}
