package com.minicloud.upms.role.controller;


import com.minicloud.common.core.page.PageReq;
import com.minicloud.common.core.util.ResponseX;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author alan.wang
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色controller")
public class RoleController {

    private final RoleService roleService;

    /**
     * @desc : 根据id查询角色
     * @return  UpmsRoleDTO
     * */
    @GetMapping("/{roleId}")
    @ApiOperation(value = "通过角色id获取角色",notes = "内部通过租户id获取")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("roleId")String roleId){
        return  ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    /**
     * @desc : 查询所有角色
     * @return  UpmsRoleDTO
     * */

    @GetMapping("/all")
    @ApiOperation(value = "获取所有角色",notes = "不分页")
    public ResponseEntity<List<RoleDTO>> all(){
        return  ResponseEntity.ok(roleService.all());
    }

    /**
     * @desc : 分页查询角色
     * @return  返回List<RoleDTO>
     * */
    @GetMapping
    @ApiOperation(value = "分页获取角色",notes = "默认每页10条")
    public ResponseEntity<PageReq> page(PageReq pageReq,RoleDTO upmsRoleDTO){
        return  ResponseEntity.ok(roleService.page(pageReq,upmsRoleDTO));
    }

    /**
     * @desc : 保存或者编辑
     * @return  返回integer roleId
     * */
    @PostMapping
    @ApiOperation(value = "保存或编辑角色",notes = "包含角色id则为编辑，反之是保存")
    public ResponseEntity<Integer> saveOrEdit(@RequestBody RoleDTO roleDTO){
        return  ResponseEntity.ok(roleService.saveOrEdit(roleDTO));
    }

    /**
     * @desc : 根据id删除角色
     * @return  返回integer roleId
     * */
    @DeleteMapping("/{roleId}")
    @ApiOperation(value = "删除角色",notes = "物理删除")
    public ResponseEntity<Integer> delete(@PathVariable("roleId") Integer roleId){
        return  ResponseEntity.ok(roleService.delete(roleId));
    }




}
