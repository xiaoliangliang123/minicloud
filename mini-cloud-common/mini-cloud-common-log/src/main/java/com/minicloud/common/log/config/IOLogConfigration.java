package com.minicloud.common.log.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOLogConfigration {

    @Bean
    public IOLogAspect ioLogAspect(){
        return new IOLogAspect();
    }
}
