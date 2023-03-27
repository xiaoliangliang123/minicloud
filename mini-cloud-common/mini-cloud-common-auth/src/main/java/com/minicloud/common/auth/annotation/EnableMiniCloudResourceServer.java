package com.minicloud.common.auth.annotation;

import com.minicloud.common.auth.config.MiniCloudResourceServerAutoConfiguration;
import com.minicloud.common.auth.config.MiniCloudSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @Author alan.wang
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({MiniCloudSecurityBeanDefinitionRegistrar.class, MiniCloudResourceServerAutoConfiguration.class})
public @interface EnableMiniCloudResourceServer {
}
