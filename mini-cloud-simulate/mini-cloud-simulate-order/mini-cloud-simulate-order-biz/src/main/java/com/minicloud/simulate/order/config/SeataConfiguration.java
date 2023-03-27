package com.minicloud.simulate.order.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author alan.wang
 */
@Configuration
public class SeataConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new SeataXidRequestInterceptor();
    }
}