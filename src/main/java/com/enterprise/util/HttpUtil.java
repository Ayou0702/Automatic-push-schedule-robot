package com.enterprise.util;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * http工具类
 *
 * @author Iwlthxcl
 * @version 1.1
 */
public class HttpUtil {

    static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
    static final ConnectionKeepAliveStrategy MY_STRATEGY;

    static {
        CONNECTION_MANAGER.setMaxTotal(1000);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(1000);
        MY_STRATEGY = (response, context) -> {
            BasicHeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));

            String param, value;

            do {

                if (!it.hasNext()) {
                    return 60000L;
                }

                HeaderElement he = it.nextElement();
                param = he.getName();
                value = he.getValue();
            } while (value == null || !"timeout".equalsIgnoreCase(param));

            return Long.parseLong(value) * 1000L;
        };
    }

    /**
     * 将请求到的内容转换为String返回
     *
     * @author Iwlthxcl
     *
     * @param url 需要请求的url
     * @return 将请求内容转换为String并返回
     * @throws IOException IO异常
     */
    public static String getUrl(String url) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String var7;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Connection", "close");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(18000).setConnectTimeout(5000).setConnectionRequestTimeout(18000).build();
            httpGet.setConfig(requestConfig);

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                Object entity;
                if (response1.getStatusLine().getStatusCode() != 200) {
                    if (response1.getStatusLine().getStatusCode() != 404) {
                        return null;
                    }

                    entity = "";
                    return (String) entity;
                }

                entity = response1.getEntity();
                String result = EntityUtils.toString((HttpEntity) entity);
                EntityUtils.consume((HttpEntity) entity);
                var7 = result;
            }
        } finally {
            httpclient.close();
        }

        return var7;
    }
}
