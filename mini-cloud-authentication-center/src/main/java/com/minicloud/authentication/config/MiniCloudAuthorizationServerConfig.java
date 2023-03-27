/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minicloud.authentication.config;


import com.minicloud.authentication.service.MiniCloudClientDetailServiceImpl;
import com.minicloud.common.auth.config.MiniCloudOAuth2RequestFactory;
import com.minicloud.common.auth.config.MiniCloudTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;


/**
 * @author alan.wang
 */
@Configuration

public class MiniCloudAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MiniCloudClientDetailServiceImpl ClientDetailServiceImpl;



    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private MiniCloudTokenEnhancer miniCloudTokenEnhancer;


    private final String MINI_CLOUD_PREFOX = "mini-cloud-token:";


    /**
     * @desc :注入client 相关内如，内存模式直接注入模拟的一个client端
     * withClient :clientId
     * secret: clientId对应密码，记得spring5.0之后前面要加上{加密方式}密文
     * authorizedGrantTypes：接受哪些授权模式，参数 grant_type 区分
     * authorities：具有哪些权限，一般针对api级别，基本没用上
     * scopes：读写级别
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("test-auth-client").secret("{bcrypt}" + new BCryptPasswordEncoder().encode("123"))
//                .authorizedGrantTypes("authorization_code", "refresh_token", "client_credentials", "password")
//                .authorities("autho2").scopes("read", "write");
//

        clients.withClientDetails(ClientDetailServiceImpl);
    }

    /**
     * @desc: 设置auth2.0本身开放的访问权限
     * 这里对/oauth/check_token 开放了表单提交权限
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
    }

    /**
     * @desc: 主要是对endpoints（框架本身的url路径）注入自定义service
     * 这里对注入了默认的userDetailsService，authenticationManager，
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT)
                .tokenStore(tokenStore())
                .tokenEnhancer(miniCloudTokenEnhancer)
                .userDetailsService(userDetailsService)
                .requestFactory(oauth2RequestFactory(ClientDetailServiceImpl))
                .authenticationManager(authenticationManager);
    }




    public OAuth2RequestFactory oauth2RequestFactory(ClientDetailsService clientDetailsService){
        MiniCloudOAuth2RequestFactory miniCloudOAuth2RequestFactory = new MiniCloudOAuth2RequestFactory(clientDetailsService);
        return miniCloudOAuth2RequestFactory;
    }



    /**
     * 使用reids 保存token 替换原来的内存存储，并设置前缀为统一mini-cloud-token: 便于查询和管理
     * */
    @Bean
    public TokenStore tokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(MINI_CLOUD_PREFOX);
        return redisTokenStore;
    }



}