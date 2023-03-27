package com.minicloud.common.auth.config;

import java.lang.annotation.*;

/**
 * @Author alan.wang
 * @desc: 仅允许feign 访问的接口注解，如果是fegin接口一定要加上
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MiniCloudFeignInterface {

    /**
     * 是否需要检验权限默认不需要
     * true：登陸後才允許調用該fegin 接口
     * false: 不登陸也可以調用該fegin 接口
     * */
    boolean enableAuth() default false;
}
