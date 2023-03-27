package com.minicloud.common.auth.config;

import cn.hutool.core.map.MapUtil;
import com.minicloud.common.auth.model.MiniCloudGrantedAuthority;
import com.minicloud.common.auth.model.MiniCloudUserDetails;
import com.minicloud.common.constant.MiniCloudCommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 */
@Slf4j
@Component
public class MiniCloudUserAuthenticationConverter implements UserAuthenticationConverter {


    /**
     * 不适用标记，即密码在这不给出来
     * */
    private static final String N_A = "N/A";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    /**
     *
     * 将check_token 中返回的OAuth2Authentication的getPrincipal 重写为我们自己的miniclouddetail
     * */
    @Override
    //@Cacheable(value = "mini_cloud_cache:authentication", key = "#responseMap['user_id']+':tenant:'+#responseMap['tenantId']", unless = "#result == null")
    public Authentication extractAuthentication(Map<String, ?> responseMap) {


        if (responseMap.containsKey(USERNAME)) {
            Map<String, ?> map = MapUtil.get(responseMap, "user_info", Map.class);
            List<Map> authorities = MapUtil.get(map,"authorities",List.class);
            List<String> roleCodes = MapUtil.get(map,"roleCodes",List.class);

            List<MiniCloudGrantedAuthority> miniCloudGrantedAuthorities = authorities.stream().map(authoritity->{
                String name =  MapUtil.getStr(authoritity,"name");
                String url = MapUtil.getStr(authoritity,"url");
                return new MiniCloudGrantedAuthority(name,url);
            }).collect(Collectors.toList());

            MiniCloudUserDetails miniCloudUserDetails = new MiniCloudUserDetails(MapUtil.getInt(map,"id"),MapUtil.getStr(map,"username"),N_A,roleCodes,miniCloudGrantedAuthorities,MapUtil.getInt(responseMap, MiniCloudCommonConstant.CommonConstant.TENANT_ID));
            return new UsernamePasswordAuthenticationToken(miniCloudUserDetails, N_A, Collections.EMPTY_LIST);
        }
        return null;
    }


}
