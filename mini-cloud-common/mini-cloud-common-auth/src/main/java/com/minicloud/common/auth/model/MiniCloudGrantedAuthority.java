package com.minicloud.common.auth.model;

import com.minicloud.common.auth.dto.PermDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 */
@Data
@AllArgsConstructor
public class MiniCloudGrantedAuthority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 2201190722835097651L;


    private String name ;


    private String url ;


    @Override
    public String getAuthority() {
        return this.url;
    }

    public static List<MiniCloudGrantedAuthority> loadAuthorities(Set<PermDTO> permDTOSet){

        List<MiniCloudGrantedAuthority> miniCloudGrantedAuthorities = permDTOSet.stream().map(upmsPermDTO -> {
            MiniCloudGrantedAuthority miniCloudGrantedAuthority = new MiniCloudGrantedAuthority(upmsPermDTO.getPermName(),upmsPermDTO.getPermUrl());
            return miniCloudGrantedAuthority;
        }).collect(Collectors.toList());
        return miniCloudGrantedAuthorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniCloudGrantedAuthority that = (MiniCloudGrantedAuthority) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
