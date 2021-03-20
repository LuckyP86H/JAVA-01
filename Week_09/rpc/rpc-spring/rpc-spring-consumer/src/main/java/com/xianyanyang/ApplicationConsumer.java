package com.xianyanyang;

import com.xianyanyang.rpc.client.loadbalance.LoadBalancer;
import com.xianyanyang.rpc.client.protocol.Protocol;
import com.xianyanyang.rpc.client.proxy.ProxyFactory;
import com.xianyanyang.rpc.client.protocol.impl.NettyProtocol;
import com.xianyanyang.rpc.client.protocol.impl.SpringRestOperationsProtocol;
import com.xianyanyang.rpc.client.loadbalance.impl.RandomLoadBalancer;
import com.xianyanyang.rpc.client.proxy.impl.CGLibProxy;
import com.xianyanyang.rpc.client.proxy.impl.DynamicProxy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ApplicationConsumer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer.class, args);
    }

    @Bean(value = "restTemplate")
    public RestOperations getRestOperations() {
        return new RestTemplateBuilder()
                .messageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new ByteArrayHttpMessageConverter(),
                        new MappingJackson2HttpMessageConverter(),
                        new FormHttpMessageConverter(),
                        new ResourceHttpMessageConverter())
                .setConnectTimeout(15000)
                .setReadTimeout(30000)
                .build();
    }

    @Bean(name = "protocol")
    public Protocol protocol(@Value("${rpc.consumer.protocol:restTemplate}") String protocol, @Autowired @Qualifier(value = "restTemplate") RestOperations restOperations) {
        if (StringUtils.equals(protocol, "restTemplate")) {
            return new SpringRestOperationsProtocol(restOperations);
        }
        return new NettyProtocol();
    }

    @Bean(name = "loadBalance")
    public LoadBalancer getRandomLoadBalancer(@Value("${rpc.consumer.loadbalance:random}") String loadBalance) {
        if (StringUtils.equals("random", loadBalance)) {
            return new RandomLoadBalancer();
        }
        throw new RuntimeException("LoadBalancer: not supported now" + loadBalance);
    }

    @Bean(name = "proxyFactory")
    public ProxyFactory getProxyFactory(@Value("${rpc.consumer.proxy:dynamic}") String proxy, @Autowired @Qualifier(value = "protocol") Protocol protocol, @Autowired @Qualifier(value = "loadBalance") LoadBalancer loadBalancer) {
        if (StringUtils.equals(proxy, "cglib")) {
            return new CGLibProxy(protocol, loadBalancer);
        }
        return new DynamicProxy(protocol, loadBalancer);
    }
}
