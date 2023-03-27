package com.minicloud.swagger.annotation;

import com.minicloud.swagger.config.MiniCloudSwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.swagger.annotation
 * @Project：mini-cloud
 * @name：EnableMinicloudSwagger
 * @Filename：EnableMinicloudSwagger
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MiniCloudSwaggerConfiguration.class)
public @interface EnableMiniCloudSwagger {
}
