package com.enterprise.util;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * http工具类
 *
 * @author PrefersMin
 * @version 1.4
 */
@Component
@RequiredArgsConstructor
public class HttpUtil {

    /**
     * 封装返回结果
     */
    private final Result result;

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

    /**
     * POST请求
     *
     * @author PrefersMin
     *
     * @param url 请求的url
     * @param data 携带的参数
     * @param header 头部信息
     * @return 返回请求结果
     * @throws IOException 可能存在的异常
     */
    public ResultVo postUrl(String url, String data, Header header) throws IOException {

        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建HttpPost请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头信息
        httpPost.setHeader(header);

        // 设置请求参数
        StringEntity requestEntity = new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED);
        httpPost.setEntity(requestEntity);

        // 发送POST请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 处理响应结果
        HttpEntity responseEntity = response.getEntity();

        String res = responseEntity != null ? EntityUtils.toString(responseEntity) : null;

        JSONObject dataObj = JSONObject.parseObject(res);

        // 关闭连接
        response.close();
        httpClient.close();

        return result.success(dataObj);

    }

}
