package com.minicloud.common.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.TENANT_ID;

/**
 * @Author alan.wang
 */
public class MiniCloudClientDetails implements ClientDetails {

    private ClientDetails clientDetails;
    public MiniCloudClientDetails(ClientDetails clientDetails){
        this.clientDetails = clientDetails;
    }

    @Override
    public String getClientId() {
        return clientDetails.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clientDetails.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return clientDetails.isSecretRequired();
    }

    @Override
    public String getClientSecret() {
        return clientDetails.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return clientDetails.isScoped();
    }

    @Override
    public Set<String> getScope() {
        return clientDetails.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return clientDetails.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return clientDetails.getRegisteredRedirectUri();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return clientDetails.getAuthorities();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDetails.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetails.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return clientDetails.isAutoApprove(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return clientDetails.getAdditionalInformation();
    }

    public String getTenantId(){
        return (String) getAdditionalInformation().get(TENANT_ID);
    }
}
