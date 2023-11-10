package com.enterprise.service.wechatService.impl;

import com.alibaba.fastjson.JSON;
import com.enterprise.service.data.entity.EnterpriseDataService;
import com.enterprise.service.wechatService.WxCoreService;
import com.enterprise.util.LogUtil;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 企业微信核心服务实现类
 *
 * @author PrefersMin
 * @version 1.8
 */
@Service
@RequiredArgsConstructor
public class WxCoreServiceImpl implements WxCoreService {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 核心服务，获取企业微信主服务对象
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
     * 核心服务，获取企业微信配置对象
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

        // 配置redis，默认启动的是6379端口
        try (JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 5000); Jedis jedis = jedisPool.getResource()) {

            String wxAccessToken = "wx" + enterpriseDataService.queryingEnterpriseData("corpId").getDataValue();
            wxCpService.setWxCpConfigStorage(wxCpDefaultConfig);
            String json = jedis.get(wxAccessToken);

            if (!StringUtils.isEmpty(json)) {
                wxCpDefaultConfig = JSON.parseObject(json, WxCpDefaultConfigImpl.class);
            }

            // 判断token是否过期
            if (wxCpDefaultConfig.isAccessTokenExpired()) {

                try {
                    // 配置token
                    String accessToken = wxCpService.getAccessToken(false);
                    wxCpDefaultConfig.setAccessToken(accessToken);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }

            }

            // 判断凭证是否过期
            if (wxCpDefaultConfig.isJsapiTicketExpired()) {

                try {
                    String jsApiTicket = wxCpService.getJsapiTicket();
                    wxCpDefaultConfig.setJsapiTicket(jsApiTicket);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }

            }

            jedis.set(wxAccessToken, JSON.toJSONString(wxCpDefaultConfig));

        }

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
            String token = wxCpService.getAccessToken(false);
           LogUtil.info(token);
            return token;
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

    }

}
