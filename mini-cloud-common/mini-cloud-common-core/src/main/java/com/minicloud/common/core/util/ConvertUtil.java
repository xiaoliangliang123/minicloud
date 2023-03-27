package com.minicloud.common.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author alan.wang
 */
public class ConvertUtil {

    public static  <T,U> List<U> convert(List<T> originSources ,Class<U> targetClass ){

        if(CollectionUtil.isEmpty(originSources)){
            return Collections.emptyList();
        }
        List<U> targetList = new ArrayList<>();
        originSources.forEach(originSource->{

            U target = BeanUtil.copyProperties(originSource,targetClass);
            targetList.add(target);
        });

        return targetList;
    }
}
