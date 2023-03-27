package com.minicloud.upms.perms.dao;


import cn.org.atool.fluent.mybatis.base.IBaseDao;
import com.minicloud.upms.perms.entity.UpmsPermEntity;

import java.util.List;

/**
 * UpmsPermDao: 数据操作接口
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
public interface UpmsPermDao extends IBaseDao<UpmsPermEntity> {
    List<UpmsPermEntity> queryRolesPermsByRoleIds(Integer[] roleIds);

    List<UpmsPermEntity> queryRolesPermsByRoleIds(Integer tenantId, Integer[] roleIds);
}
