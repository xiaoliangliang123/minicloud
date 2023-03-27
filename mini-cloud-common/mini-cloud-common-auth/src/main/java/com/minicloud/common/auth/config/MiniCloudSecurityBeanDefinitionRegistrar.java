package com.minicloud.common.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author alan.wang
 */
@Slf4j
public class MiniCloudSecurityBeanDefinitionRegistrar  implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (registry.isBeanNameInUse("miniCloudResourceServerConfig")) {
            log.warn("本地存在资源服务器配置，覆盖默认配置:" + "miniCloudResourceServerConfig");
            return;
        }

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MiniCloudResourceServerConfig.class);
        registry.registerBeanDefinition("miniCloudResourceServerConfig", beanDefinition);

    }
}
