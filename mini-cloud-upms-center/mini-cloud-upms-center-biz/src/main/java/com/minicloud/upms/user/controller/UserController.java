package com.minicloud.upms.user.controller;

import com.minicloud.common.auth.config.MiniCloudFeignInterface;
import com.minicloud.common.core.page.PageReq;
import com.minicloud.common.core.util.ResponseX;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.user.dto.UserDTO;
import com.minicloud.upms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.TENANT_ID;

/**
 * @Author alan.wang
 */
@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "用户controller")
public class UserController {


    private final UserService upmsUserService;

    /**
     * @desc : 分页查询用户
     * @return  返回List<PageReq>
     * */
    @GetMapping
    @ApiOperation(value = "分页查询用户",notes = "默认每页10条")
    public ResponseEntity<PageReq> page(PageReq pageReq, RoleDTO upmsRoleDTO){
        return  ResponseEntity.ok(upmsUserService.page(pageReq,upmsRoleDTO));
    }

    /**
     * @desc :保存upms user
     * @param upmsUserDTO
     * @return 保存后用户的userId
     * */
    @PostMapping
    @ApiOperation(value = "保存或编辑用户",notes = "包含用户id则是编辑，反之是保存")
    public ResponseEntity saveUserOrEdit(@RequestBody UserDTO upmsUserDTO){
        return ResponseEntity.ok(upmsUserService.saveOrEditUser(upmsUserDTO));
    }

    /**
     * @desc :根据userId 查询user
     * @param userId
     * @return  返回userDTO
     * */
    @GetMapping("/findById/{userId}")
    @ApiOperation(value = "通过用户id查询用户")
    public ResponseEntity findUserById(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(upmsUserService.findUserById(userId));
    }


    /**
     * @desc :根据username 查询user
     * @param username
     * @return  返回userDTO
     * */
    @GetMapping("/findByUsername/{username}")
    @ApiOperation(value = "通过用户名查询用户",notes = "内部通过租户id")
    public ResponseEntity findByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(upmsUserService.findByUsername(username));
    }

    /**
     * @desc :对fegin根据username 查询user
     * @param username
     * @return  返回userDTO
     * */
    @MiniCloudFeignInterface
    @GetMapping("/fegin/findByUsername/{username}")
    @ApiOperation(value = "通过用户名查询用户",notes = "仅供feign内部使用")
    public ResponseEntity feignFindByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(upmsUserService.findByUsername(username));
    }

    /**
     * @desc :对fegin根据username 和tenantId 查询user
     * @param username
     * @return  返回userDTO
     * */
    @MiniCloudFeignInterface
    @GetMapping("/fegin/findByTenantIdAndUsername/{tenantId}/{username}")
    @ApiOperation(value = "通过用租户id和户名查询用户",notes = "仅供feign内部使用")
    public ResponseEntity feignFindByTenantIdAndUsername(@PathVariable(TENANT_ID) Integer tenantId,@PathVariable("username") String username){
        return ResponseEntity.ok(upmsUserService.findByTenantIdAndUsername(tenantId,username));
    }


    /**
     * @desc :对fegin根据userId 查询user
     * @param userId
     * @return  返回userDTO
     * */
    @GetMapping("/fegin/feignFindUserById/{userId}")
    @ApiOperation(value = "通过用户id查询用户")
    public ResponseEntity feignFindUserById(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(upmsUserService.findUserById(userId));
    }

    /**
     * @desc :根据userId删除用户
     * @param userId
     * @return  返回userDTO
     * */
    @DeleteMapping("/{userId}")
    @ApiOperation(value = "根据userId删除用户",notes = "逻辑删除")
    public ResponseX removeUserByUserId(@PathVariable("userId") Integer userId){
        return ResponseX.ok(upmsUserService.deleteByUserId(userId));
    }

}
