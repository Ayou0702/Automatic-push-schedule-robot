package com.enterprise.service;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

/**
 * 企业微信核心服务
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface WxCoreService {

    /**
     * 核心服务
     *
     * @author PrefersMin
     *
     * @return 返回写入了agentId、secret、corpId、token配置的一个WxCpService类型的对象
     */
    WxCpService getWxCpService();

    /**
     * 核心服务
     *
     * @author PrefersMin
     *
     * @return 返回写入了agentId、secret、corpId配置的一个WxCpDefaultConfigImpl类型的对象
     */
    WxCpDefaultConfigImpl getWxCpDefaultConfig();

    /**
     * 重置token和jsApi并写入核心服务对象
     *
     * @author PrefersMin
     *
     * @param wxCpService 企业微信主服务对象
     * @param wxCpDefaultConfig 企业微信配置
     */
    void resetTokenAndJsApi(WxCpService wxCpService, WxCpDefaultConfigImpl wxCpDefaultConfig);

    /**
     * 获取token明文
     * 此方法需要调用主服务，需要传参
     *
     * @author PrefersMin
     *
     * @return 返回token
     */
    String getAccessToken();

}
