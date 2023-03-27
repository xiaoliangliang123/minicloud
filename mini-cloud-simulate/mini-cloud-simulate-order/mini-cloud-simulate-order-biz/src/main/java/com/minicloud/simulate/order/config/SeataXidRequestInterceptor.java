package com.minicloud.simulate.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author alan.wang
 */
public class SeataXidRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();

        if (StringUtils.isEmpty(xid)) {
            return;
        }

        List<String> fescarXid = new ArrayList<>();
        fescarXid.add(xid);
        template.header(RootContext.KEY_XID, fescarXid);
        System.err.println("添加XID:" + fescarXid);
    }

}