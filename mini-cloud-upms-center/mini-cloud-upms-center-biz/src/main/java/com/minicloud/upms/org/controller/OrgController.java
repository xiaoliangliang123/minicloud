package com.minicloud.upms.org.controller;

import com.minicloud.upms.org.dto.OrgDTO;
import com.minicloud.upms.org.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author alan.wang
 */
@RestController
@RequestMapping("/org")
@AllArgsConstructor
@Api(value = "组织机构controller")
public class OrgController {

    private final OrgService orgService;

    @GetMapping
    @ApiOperation(value = "查询所有组织机构",notes = "不分页")
    public ResponseEntity<List<OrgDTO>> list(OrgDTO orgDTO){
        return ResponseEntity.ok(orgService.list(orgDTO));
    }

    @GetMapping("/{orgId}")
    @ApiOperation(value = "根据机构id查询组织机构信息")
    public ResponseEntity<OrgDTO> findById(@PathVariable("orgId")Integer orgId){
        return ResponseEntity.ok(orgService.findById(orgId));
    }

    @PostMapping
    @ApiOperation(value = "保存或者编辑组织机构",notes = "传入参数有机构id则为编辑，反正则是保存")
    public ResponseEntity<OrgDTO> saveOrEdit(@RequestBody OrgDTO orgDTO){
        return ResponseEntity.ok(orgService.saveOrEdit(orgDTO));
    }

    @DeleteMapping("/{orgId}")
    @ApiOperation(value = "删除组织机构",notes = "物理删除")
    public ResponseEntity<Integer> deleteById(@PathVariable("orgId")Integer orgId) throws Exception {
        return ResponseEntity.ok(orgService.deleteById(orgId));
    }
}
