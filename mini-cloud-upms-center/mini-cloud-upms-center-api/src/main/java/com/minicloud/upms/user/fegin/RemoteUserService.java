package com.minicloud.upms.user.fegin;

import com.minicloud.upms.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.TENANT_ID;
import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.FEIGN_REQUEST;
import static com.minicloud.common.constant.MiniCloudCommonConstant.ServerConstant.MINI_CLOUD_SERVER_UPMS;

/**
 * @Author alan.wang
 * @desc: upms center 对外暴漏的fegin接口
 */

@FeignClient(contextId = "remoteUserService",value = MINI_CLOUD_SERVER_UPMS)
public interface RemoteUserService {


    /**
     * 通过username 查询出 UpmsUserDTO
     * */

    @GetMapping("/user/fegin/findByUsername/{username}")
    UserDTO queryUpmsUserByUsername(@PathVariable("username")  String username, @RequestHeader(FEIGN_REQUEST) String fegin);

    /**
     * 通过username 和tenantId 查询出 UpmsUserDTO
     * */

    @GetMapping("/user/fegin/findByTenantIdAndUsername/{tenantId}/{username}")
    UserDTO queryUpmsUserByTenantIdAndUsername(@PathVariable(TENANT_ID)  Integer tenantId, @PathVariable("username")  String username, @RequestHeader(FEIGN_REQUEST) String fegin);

    /**
     * 通过userId 查询出 UpmsUserDTO
     * */
    @GetMapping("/user/findById/{userId}")
    UserDTO queryUpmsUserById(@PathVariable("userId")  Integer userId);
}
