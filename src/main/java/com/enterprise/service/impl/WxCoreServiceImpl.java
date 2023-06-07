package com.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.service.WxCoreService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * 企业微信主服务
 *
 * @author PrefersMin
 * @version 1.4
 */
@Service
public class WxCoreServiceImpl implements WxCoreService {

    String accessToken = null;

    /**
     * 企业数据接口
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    /**
     * 核心服务
     *
     * @author PrefersMin
     *
     * @return 返回写入了agentId、secret、corpId、token配置的一个WxCpService类型的对象
     */
    public WxCpService getWxCpService() {

        WxCpService wxCpService = new WxCpServiceImpl();
        WxCpDefaultConfigImpl config = getWxCpDefaultConfig();

        // 配置token
        resetTokenAndJsApi(wxCpService, config);

        return wxCpService;
    }

    /**
     * 核心服务
     *
     * @author PrefersMin
     *
     * @return 返回写入了agentId、secret、corpId配置的一个WxCpDefaultConfigImpl类型的对象
     */
    public WxCpDefaultConfigImpl getWxCpDefaultConfig() {

        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();

        // 配置agentId、secret、corpId
        config.setAgentId(Integer.valueOf(enterpriseDataService.queryingEnterpriseData("agentId").getDataValue()));
        config.setCorpSecret(enterpriseDataService.queryingEnterpriseData("secret").getDataValue());
        config.setCorpId(enterpriseDataService.queryingEnterpriseData("corpId").getDataValue());

        return config;

    }

    /**
     * 重置token和jsApi并写入核心服务对象
     *
     * @author PrefersMin
     *
     * @param wxCpService 企业微信主服务对象
     * @param wxCpDefaultConfig 企业微信配置
     */
    public void resetTokenAndJsApi(WxCpService wxCpService, WxCpDefaultConfigImpl wxCpDefaultConfig) {
        // 配置redis
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxTotal(18);
        // redis启动后，默认启动的是6379端口
        Jedis jedis = new JedisPool(jedisPoolConfig, "localhost", 6379, 5000).getResource();

        wxCpService.setWxCpConfigStorage(wxCpDefaultConfig);
        String wxAccessToken = "wx" + enterpriseDataService.queryingEnterpriseData("corpId").getDataValue();
        String json = jedis.get(wxAccessToken);
        if (!StringUtils.isEmpty(json)) {
            wxCpDefaultConfig = JSON.parseObject(json, WxCpDefaultConfigImpl.class);
        }
        if (wxCpDefaultConfig.isAccessTokenExpired()) {
            try {
                // 配置token
                accessToken = wxCpService.getAccessToken(false);
                wxCpDefaultConfig.setAccessToken(accessToken);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        if (wxCpDefaultConfig.isJsapiTicketExpired()) {
            String jsApi;
            try {
                jsApi = wxCpService.getJsapiTicket();
                wxCpDefaultConfig.setJsapiTicket(jsApi);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }

        jedis.set(wxAccessToken, JSON.toJSONString(wxCpDefaultConfig));
        jedis.close();

    }

    /**
     * 获取token明文
     * 此方法需要调用主服务，需要传参
     *
     * @author PrefersMin
     *
     * @return 返回token
     */
    public String getAccessToken() {
        WxCpService wxCpService = getWxCpService();
        wxCpService.setWxCpConfigStorage(getWxCpDefaultConfig());
        try {
            accessToken = wxCpService.getAccessToken(false);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }
}
