package com.minicloud.common.auth.config;

import com.minicloud.common.auth.model.MiniCloudUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author alan.wang
 */
public class MiniCloudAuthenticationUtil {

    public static final Integer EMPTY_TENANT_ID = 1;

    private static ThreadLocal<Integer> currentTenantIds = new ThreadLocal<>();

    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static MiniCloudUserDetails getUserDetails(){
        return (MiniCloudUserDetails)getAuthentication().getPrincipal();
    }


    public static void setTenantId(Integer tenantId){
        currentTenantIds.set(tenantId);
    }

    public static void cleanTenantId(){
        currentTenantIds.remove();
    }

    public static Integer currentTenantId(){
        return currentTenantIds.get();
    }
}
