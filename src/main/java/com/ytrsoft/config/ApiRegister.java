package com.ytrsoft.config;

import com.ytrsoft.core.Props;
import com.ytrsoft.http.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.lang.Nullable;

@Configuration
public class ApiRegister {

    private final Props props;

    public ApiRegister(Props props) {
        this.props = props;
    }

    public ProxyFactoryBean createProxyBean(@Nullable Class<?> targetClass) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTargetClass(targetClass);
        proxyFactoryBean.setInterceptorNames("apiInvocationHandler");
        return proxyFactoryBean;
    }

    @Bean
    public ApiHandler apiInvocationHandler() {
        return new ApiHandler(props);
    }

    @Bean
    public ProxyFactoryBean userApi() {
        return createProxyBean(UserApi.class);
    }

    @Bean
    public ProxyFactoryBean profileApi() {
        return createProxyBean(ProfileApi.class);
    }

    @Bean
    public ProxyFactoryBean nearlyApi() {
        return createProxyBean(NearlyApi.class);
    }

    @Bean
    public ProxyFactoryBean commentApi() {
        return createProxyBean(CommentApi.class);
    }

    @Bean
    public ProxyFactoryBean timelineApi() {
        return createProxyBean(TimelineApi.class);
    }

    @Bean
    public ProxyFactoryBean newApi() {
        return createProxyBean(NewApi.class);
    }
}