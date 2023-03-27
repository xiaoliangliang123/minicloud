package com.minicloud.common.auth.config;

import com.minicloud.common.auth.model.MiniCloudUserDetails;
import com.minicloud.common.constant.MiniCloudCommonConstant;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.minicloud.common.constant.MiniCloudCommonConstant.LicenceConstant.*;
import static com.minicloud.common.constant.MiniCloudCommonConstant.UserConstant.*;

/**
 * @Author alan.wang
 */
@Component
public class MiniCloudTokenEnhancer implements TokenEnhancer {



    /**
     * 扩展auth 认证中map 存放token 内容，client 模式不处理
     * */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
            return accessToken;
        }

        final Map<String, Object> additionalInfo = new HashMap<>(8);
        MiniCloudUserDetails miniCloudUserDetails = (MiniCloudUserDetails) authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put(DETAILS_USER, miniCloudUserDetails);
        additionalInfo.put(LICENSE,DOMAIN);
        additionalInfo.put(AUTHOR,ALAN_WANG);
        additionalInfo.put(CONTACT,CONTACT_WECHAT);
        additionalInfo.put(ACTIVE, Boolean.TRUE);
        additionalInfo.put(MiniCloudCommonConstant.CommonConstant.TENANT_ID,authentication.getOAuth2Request().getExtensions().get(MiniCloudCommonConstant.CommonConstant.TENANT_ID));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
