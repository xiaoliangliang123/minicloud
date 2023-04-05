package com.minicloud.order.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minicloud.order.dubbo.entity.OrderEntiry;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.mapper
 * @Project：mini-cloud-gateaway-center
 * @name：OrderMapper
 * @Date：2023/4/5 16:16
 * @Filename：OrderMapper
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntiry> {


}
