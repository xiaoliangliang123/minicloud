package com.minicloud.authentication.service;

import com.minicloud.common.auth.model.MiniCloudClientDetails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @Author alan.wang
 */
@Service
public class MiniCloudClientDetailServiceImpl extends JdbcClientDetailsService {


    public MiniCloudClientDetailServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    //@Cacheable(value = "mini_cloud_cache:client:details", key = "#clientId", unless = "#result == null")
    public MiniCloudClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails =  super.loadClientByClientId(clientId);
        return new MiniCloudClientDetails(clientDetails);
    }
}
