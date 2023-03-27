package com.minicloud.common.fegin;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author alan.wang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
@Import({MiniCloudFeginBeanDefinitionRegistrar.class})
public @interface  EnableMiniCloudFeignClients{


    String[] value() default {};

    /**
     * 主要是为了扫描我们自己框架的包
     * */
    String[] basePackages() default {"com.minicloud"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};


}
