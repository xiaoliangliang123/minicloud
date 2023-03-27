package com.minicloud.upms.org.service;

import com.minicloud.upms.org.dto.OrgDTO;

import java.util.List;

/**
 * @Author alan.wang
 */
public interface OrgService {

    List<OrgDTO> list(OrgDTO upmsOrgDTO);

    OrgDTO saveOrEdit(OrgDTO orgDTO);

    OrgDTO findById(Integer orgId);

    Integer deleteById(Integer orgId) throws Exception;
}
