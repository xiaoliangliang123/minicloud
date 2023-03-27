package com.minicloud.upms.perms.controller;

import com.minicloud.common.core.util.ResponseX;
import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.perms.service.PermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/perms")
@AllArgsConstructor
@Api(value = "权限controller")
public class UserPermsController {

    private final PermService permService;

    @PutMapping("/{roleId}")
    @ApiOperation(value = "通过角色id保存权限")
    public ResponseX save(@PathVariable("roleId")Integer roleId, @RequestBody Set<PermDTO> permDTOS){

        return ResponseX.ok(permService.savePerms(roleId,permDTOS));
    }

    @GetMapping
    @ApiOperation(value = "通过角色id获取角色权限")
    public ResponseX queryPerms(@RequestParam("roleIds")Integer[] roleIds){

        return ResponseX.ok(permService.queryRolesPermsByRoleIds(roleIds));
    }
}
