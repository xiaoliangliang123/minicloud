package com.minicloud.upms.org.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.upms.org.dao.UpmsOrgDao;
import com.minicloud.upms.org.dto.OrgDTO;
import com.minicloud.upms.org.entity.UpmsOrgEntity;
import com.minicloud.upms.org.service.OrgService;
import com.minicloud.upms.org.wrapper.UpmsOrgQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author alan.wang
 */
@Service
@AllArgsConstructor
public class OrgServiceImpl implements OrgService {

    private final UpmsOrgDao upmsOrgDao;

    @Override
    public List<OrgDTO> list(OrgDTO upmsOrgDTO) {

        Integer tenantId = MiniCloudAuthenticationUtil.getUserDetails().getTenantId();

        if(Objects.isNull(upmsOrgDTO.getOrgParentId())|| NumberUtil.equals(new BigDecimal(upmsOrgDTO.getOrgParentId()),BigDecimal.ZERO)){

            return upmsOrgDao.mapper().listPoJos(OrgDTO.class,new UpmsOrgQuery()
                    .where.tenantId().eq(tenantId)
                    .orgParentId().eq(0).end());
        }else {
            return upmsOrgDao.mapper().listPoJos(OrgDTO.class,new UpmsOrgQuery()
                    .where.tenantId().eq(tenantId)
                    .orgParentId().eq(upmsOrgDTO.getOrgParentId()).end());
        }
    }

    @Override
    public OrgDTO saveOrEdit(OrgDTO upmsOrgDTO) {

        if(Objects.isNull(upmsOrgDTO.getOrgId())) {
            UpmsOrgEntity upmsOrgEntity = new UpmsOrgEntity();
            upmsOrgEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            BeanUtils.copyProperties(upmsOrgDTO, upmsOrgEntity);
            upmsOrgEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            upmsOrgDao.mapper().insert(upmsOrgEntity);
            upmsOrgDTO.setOrgId(upmsOrgEntity.getOrgId());
        }else {
            UpmsOrgEntity upmsOrgEntity = new UpmsOrgEntity();
            BeanUtils.copyProperties(upmsOrgDTO, upmsOrgEntity);
            upmsOrgEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            upmsOrgDao.mapper().updateById(upmsOrgEntity);
            upmsOrgDTO.setOrgId(upmsOrgEntity.getOrgId());
        }
        return upmsOrgDTO;
    }

    @Override
    public OrgDTO findById(Integer orgId) {
        return (OrgDTO)upmsOrgDao.mapper().findOne(OrgDTO.class,new UpmsOrgQuery().where.orgId().eq(orgId).end()).get();
    }

    @Override
    public Integer deleteById(Integer orgId) throws Exception {

        Objects.requireNonNull(orgId,"orgid can not be null");
        Integer count = upmsOrgDao.mapper().count(new UpmsOrgQuery().where.orgParentId().eq(orgId).end());
        if(count >0){
            throw new Exception("orgId:"+orgId+"has child ,can not be delete");
        }
        return upmsOrgDao.mapper().deleteById(orgId);
    }

}
