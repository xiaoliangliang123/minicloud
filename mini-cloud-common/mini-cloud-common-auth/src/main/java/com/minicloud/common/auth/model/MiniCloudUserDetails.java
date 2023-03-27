package com.minicloud.common.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author alan.wang
 * @desc: 保存到oauth 缓存中的数据，携带自定义属性的话需要自己添加
 */
public class MiniCloudUserDetails  extends User  {

    private Integer id;
    private Integer tenantId;
    private List<String> roleCodes;
    private Collection<GrantedAuthority> miniCloudGrantedAuthorities;

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public MiniCloudUserDetails(Integer id,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, Collections.EMPTY_LIST);
        this.id = id;
        this.miniCloudGrantedAuthorities = (Collection<GrantedAuthority>) authorities;
    }

    public MiniCloudUserDetails(Integer id, String username, String password, List<String> roleCodes, Collection<? extends GrantedAuthority> authorities, Integer tenantId) {
        super(username, password, Collections.EMPTY_LIST);
        this.id = id;
        this.tenantId = tenantId;
        this.roleCodes = roleCodes;
        this.miniCloudGrantedAuthorities = (Collection<GrantedAuthority>) authorities;
    }





    public MiniCloudUserDetails(Integer id,String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, Collections.EMPTY_LIST);
        this.id = id;
        this.miniCloudGrantedAuthorities = (Collection<GrantedAuthority>) authorities;
    }

    public Integer getId() {
        return id;
    }



    public Integer getTenantId() {
        return tenantId;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.miniCloudGrantedAuthorities;
    }
}
