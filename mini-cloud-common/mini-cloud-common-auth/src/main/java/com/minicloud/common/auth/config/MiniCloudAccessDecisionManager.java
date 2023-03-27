package com.minicloud.common.auth.config;

import com.minicloud.common.auth.model.MiniCloudGrantedAuthority;
import com.minicloud.common.auth.model.MiniCloudUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author alan.wang
 */
@Slf4j
@Component
public class MiniCloudAccessDecisionManager implements AccessDecisionManager {


    @Autowired
    private CacheManager cacheManager;

    private final String SUPER_ADMIN = "SUPER_ADMIN";

    /**
     * @desc :通过获取自定义的MiniCloudUserDetails
     * 取得当前登陆人的所有grantedAuthorities 然后一 一和当前访问路径匹配，如果请求方式与url均一致则说明认证成功
     * 否则抛出AccessDeniedException 异常
     */
    @Override
    public void decide(Authentication authentication, Object filterInvocation, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

//        long start = System.currentTimeMillis();
//        String requestUrl = ((FilterInvocation) filterInvocation).getRequestUrl();
//        String method = ((FilterInvocation) filterInvocation).getRequest().getMethod();
//        if (authentication.getPrincipal() instanceof String) {
//            throw new AccessDeniedException(authentication.getName() + ",无权访问url:" + requestUrl);
//        }
//        Collection<MiniCloudGrantedAuthority> grantedAuthorities = ((MiniCloudUserDetails) authentication.getPrincipal()).getMiniCloudGrantedAuthorities();
//        for (MiniCloudGrantedAuthority grantedAuthority : grantedAuthorities) {
//            if (grantedAuthority.getAuthority().equals(SUPER_ADMIN)) {
//                  return;
//            }
//            if (requestUrl.equals(grantedAuthority.getAuthority()) && method.equals(grantedAuthority.getMethod())) {
//                long end = System.currentTimeMillis();
//                log.warn("MiniCloudAccessDecisionManager decide use:" + (end - start));
//                return;
//            }
//        }
//        throw new AccessDeniedException(authentication.getName() + ",无权访问url:" + requestUrl);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
