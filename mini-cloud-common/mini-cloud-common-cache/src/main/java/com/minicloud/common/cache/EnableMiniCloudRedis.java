package com.minicloud.common.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author alan.wang
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(MiniCloudRedisTemplateConfig.class)
public @interface EnableMiniCloudRedis {
}
