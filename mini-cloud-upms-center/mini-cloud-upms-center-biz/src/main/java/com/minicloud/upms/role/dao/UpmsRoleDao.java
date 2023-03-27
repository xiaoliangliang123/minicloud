package com.minicloud.upms.role.dao;


import cn.org.atool.fluent.mybatis.base.IBaseDao;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.entity.UpmsRoleEntity;

import java.util.List;

/**
 * UpmsRoleDao: 数据操作接口
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
public interface UpmsRoleDao extends IBaseDao<UpmsRoleEntity> {

    List<RoleDTO> queryRolesByUserId(Integer userId);

    List<RoleDTO> queryRolesByTenantIdAndUserId(Integer tenantId, Integer userId);
}
