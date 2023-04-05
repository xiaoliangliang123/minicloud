package com.minicloud.goods.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minicloud.goods.dubbo.dto.GoodsDTO;
import com.minicloud.goods.dubbo.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo.mapper
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsMapper
 * @Date：2023/4/3 19:59
 * @Filename：GoodsMapper
 */
@Mapper
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

    void subStock(@Param("goodsDTO") GoodsDTO goodsDTO);
}
