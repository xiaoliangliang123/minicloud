package com.minicloud.upms.org.service.impl;

import com.minicloud.upms.org.dao.UpmsOrgTagDao;
import com.minicloud.upms.org.dto.OrgTagDTO;
import com.minicloud.upms.org.service.OrgTagService;
import com.minicloud.upms.org.wrapper.UpmsOrgTagQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrgTagServiceImpl implements OrgTagService {

    @Resource
    private UpmsOrgTagDao upmsOrgTagDao;

    @Override
    public List<OrgTagDTO> list() {
        return upmsOrgTagDao.mapper().listPoJos(OrgTagDTO.class,new UpmsOrgTagQuery());

    }


}
