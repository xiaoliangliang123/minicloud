package com.minicloud.upms.user.dao;

import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.upms.user.dao.base.UpmsUserBaseDao;
import com.minicloud.upms.user.entity.UpmsUserEntity;
import com.minicloud.upms.user.wrapper.UpmsUserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class UpmsUserDaoImpl extends UpmsUserBaseDao implements UpmsUserDao {

    /**
     * @desc:根据username 查询user
     * */
    @Override
    public UpmsUserEntity findByName(String username) {

        IQuery iQuery = new UpmsUserQuery().select.userId().username().password().end().where.username().eq(username).deleted().eq(false).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end();
        return this.mapper.findOne(iQuery);
    }

    /**
     * @desc: 查询所有user
     * */
    @Override
    public List<UpmsUserEntity> queryAllUsers() {
        IQuery iQuery = new UpmsUserQuery().select.userId().username().end();
        return this.mapper.listEntity(iQuery);
    }

    @Override
    public UpmsUserEntity findByTenantIdAndUsername(Integer tenantId, String username) {
        IQuery iQuery = new UpmsUserQuery().select.userId().username().password().tenantId().end().where.tenantId().eq(tenantId).deleted().eq(false).and.username().eq(username).end();
        return this.mapper.findOne(iQuery);    }
}
