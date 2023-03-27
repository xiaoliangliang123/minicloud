package com.minicloud.upms.org.controller;

import com.minicloud.upms.org.dto.OrgTagDTO;
import com.minicloud.upms.org.service.OrgTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orgTag")
@AllArgsConstructor
@Api(value = "组织机构标签controller")
public class OrgTagController {

    private final OrgTagService orgTagService;

    @GetMapping
    @ApiOperation(value = "查询所有组织机构标签",notes = "不分页")
    public ResponseEntity<List<OrgTagDTO>> list(){
        return ResponseEntity.ok(orgTagService.list());
    }


}
