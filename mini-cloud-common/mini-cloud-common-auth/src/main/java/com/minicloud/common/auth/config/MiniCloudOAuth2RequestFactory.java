package com.minicloud.common.auth.config;

import com.minicloud.common.constant.MiniCloudCommonConstant;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.TENANT_ID;

/**
 * @Author alan.wang
 */
public class MiniCloudOAuth2RequestFactory extends DefaultOAuth2RequestFactory {

    public MiniCloudOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    @Override
    public OAuth2Request createOAuth2Request(ClientDetails client, TokenRequest tokenRequest) {
        OAuth2Request oAuth2Request =  super.createOAuth2Request(client, tokenRequest);
        oAuth2Request.getExtensions().put(TENANT_ID,(Integer)client.getAdditionalInformation().get(TENANT_ID));
        return oAuth2Request;
    }
}
