package com.minicloud.common.auth.config;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.FEIGN;
import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.FEIGN_REQUEST;

/**
 * @Author alan.wang
 * @desc:判断header 中是否包含 feign-request
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class MiniCloudFeignSecurityAspect {


    private final HttpServletRequest request;


    @SneakyThrows
    @Around("@annotation(miniCloudFeignInterface)")
    public Object around(ProceedingJoinPoint point, MiniCloudFeignInterface miniCloudFeignInterface) {
        String header = request.getHeader(FEIGN_REQUEST);
        if (!StrUtil.equals(FEIGN, header)) {
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }




}
