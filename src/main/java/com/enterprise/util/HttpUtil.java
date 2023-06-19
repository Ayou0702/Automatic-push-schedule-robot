package com.enterprise.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * http工具类
 *
 * @author PrefersMin
 * @version 1.3
 */
public class HttpUtil {

    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000);
        connectionManager.setDefaultMaxPerRoute(1000);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(18000).setConnectTimeout(5000).setConnectionRequestTimeout(18000).build();

        HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 将请求到的内容转换为String返回
     *
     * @author PrefersMin
     *
     * @param url 需要请求的url
     * @return 将请求内容转换为String并返回
     * @throws IOException IO异常
     */
    public static String getUrl(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Connection", "close");

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                if (response.getStatusLine().getStatusCode() != 404) {
                    return null;
                }
                return "";
            }
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return result;
        }
    }
}
