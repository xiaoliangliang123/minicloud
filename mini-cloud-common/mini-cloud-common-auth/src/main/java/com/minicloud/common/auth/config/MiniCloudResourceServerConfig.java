package com.minicloud.common.auth.config;

import com.minicloud.common.auth.config.MiniCloudAccessDecisionManager;
import com.minicloud.common.auth.config.MiniCloudIgnoreAndFeignUrlIniter;
import com.minicloud.common.auth.config.MiniCloudUserAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * @Author alan.wang
 */
@Slf4j
public class MiniCloudResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    protected RemoteTokenServices remoteTokenServices;

    @Autowired
    protected DiscoveryClient discoveryClient;

    @Autowired
    protected RestTemplate lbRestTemplate;

    @Autowired
    private MiniCloudUserAuthenticationConverter miniCloudUserAuthenticationConverter;



    @Autowired
    private MiniCloudIgnoreAndFeignUrlIniter ignoreAndFeignUrlIniter;
    /**
     * @desc: 设置需要拦截或者开放的url
     * 这里开放了/oauth/** 框架本身的url
     * ignoreAndFeignUrlIniter.getIgnoreUrls() 是开放 @MiniCloudFeignInterface 注解开放的url
     * */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry= httpSecurity
                .authorizeRequests();
        expressionInterceptUrlRegistry.antMatchers("/actuator/**","/tenant/**","/v2/**").permitAll().antMatchers("/oauth/**").permitAll();
        ignoreAndFeignUrlIniter.getUrls().forEach(url->expressionInterceptUrlRegistry.antMatchers(url).permitAll());
        expressionInterceptUrlRegistry.anyRequest().authenticated();
    }

    /**
     * @desc: 设置Converter 将默认的user Authentication 返回改完返回自定义的miniclouduserdetil
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(miniCloudUserAuthenticationConverter);

        remoteTokenServices.setRestTemplate(lbRestTemplate);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);

    }

}
