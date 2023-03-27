package com.minicloud.upms.role.feign;

import com.minicloud.upms.role.dto.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import static com.minicloud.common.constant.MiniCloudCommonConstant.ServerConstant.MINI_CLOUD_SERVER_UPMS;

/**
 * @Author alan.wang
 */
@FeignClient(contextId = "upmsCenterRemoteRoleService",value = MINI_CLOUD_SERVER_UPMS)
public interface RemoteRoleService {

    /**
     * 通过username 查询出 UpmsUserDTO
     * */
    @GetMapping("/user/fegin/role/all")
    List<RoleDTO> queryUpmsAllRolePerms();
}
