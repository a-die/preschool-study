package com.preschool.config;

import com.preschool.utils.ApplicationValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description http工厂配置
 * @date
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private ApplicationValues appValues;

    @Bean
    public RestTemplate restTemplate() {
//        return new RestTemplate(httpRequestFactory());
        return new RestTemplate();
    }

//    @Bean
//    public ClientHttpRequestFactory httpRequestFactory() {
//        return new HttpComponentsClientHttpRequestFactory(httpClient());
//    }
//
//    @Bean
//    public HttpClient httpClient() {
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", SSLConnectionSocketFactory.getSocketFactory())
//                .build();
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//        connectionManager.setMaxTotal(appValues.getMaxTotal());
//        connectionManager.setDefaultMaxPerRoute(appValues.getMaxPerRoute());
//        connectionManager.setValidateAfterInactivity(appValues.getInactivity());
//        RequestConfig requestConfig = RequestConfig.custom()
//                //服务器返回数据(response)的时间，超过抛出read timeout
//                .setSocketTimeout(appValues.getSocketTimeout())
//                //连接上服务器(握手成功)的时间，超出抛出connect timeout
//                .setConnectTimeout(appValues.getConnTimeOut())
//                //从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
//                .setConnectionRequestTimeout(appValues.getConnReqTimeOut())
//                .build();
//        return HttpClientBuilder.create()
//                .setDefaultRequestConfig(requestConfig)
//                .setConnectionManager(connectionManager)
//                .build();
//    }
}
