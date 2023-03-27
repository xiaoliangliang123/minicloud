package com.minicloud.common.fegin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author alan.wang
 */
@Slf4j
public class MiniCloudFeginBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (registry.isBeanNameInUse("miniCloudFeignRequestInterceptor")) {
            log.warn("本地存在feginrequest配置，覆盖默认配置:" + "miniCloudFeignRequestInterceptor");
            return;
        }

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MiniCloudFeignRequestInterceptor.class);
        registry.registerBeanDefinition("miniCloudFeignRequestInterceptor", beanDefinition);

    }
}
