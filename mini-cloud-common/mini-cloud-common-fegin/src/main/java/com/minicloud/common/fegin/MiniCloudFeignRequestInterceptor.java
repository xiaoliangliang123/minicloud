package com.minicloud.common.fegin;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.FEIGN;
import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.AUTHORIZATION;
import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.FEIGN_REQUEST;

/**
 * @Author alan.wang
 */

@Component
public class MiniCloudFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        if(!Objects.isNull(RequestContextHolder.getRequestAttributes())) {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            requestTemplate.header(AUTHORIZATION, httpServletRequest.getHeader(AUTHORIZATION));
            requestTemplate.header(FEIGN_REQUEST, FEIGN);
        }
    }
}
