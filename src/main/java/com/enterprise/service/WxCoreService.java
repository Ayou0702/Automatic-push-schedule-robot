package com.enterprise.service;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

public interface WxCoreService {

    WxCpService getWxCpService();

    WxCpDefaultConfigImpl getWxCpDefaultConfig();

    void resetTokenAndJsApi(WxCpService wxCpService, WxCpDefaultConfigImpl wxCpDefaultConfig);

    String getAccessToken();

}
