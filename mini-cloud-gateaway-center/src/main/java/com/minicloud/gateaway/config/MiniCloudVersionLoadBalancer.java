/*
 *    Copyright (c) 2018-2025, orient All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the yingcan.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: orient
 */

package com.minicloud.gateaway.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Map;

import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.VERSION;

@Slf4j
@AllArgsConstructor
public class MiniCloudVersionLoadBalancer implements MiniCloudLoadBalancer {
	private DiscoveryClient discoveryClient;



	@Override
	public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

		//注册中心无实例 抛出异常
		if (CollUtil.isEmpty(instances)) {
			log.warn("No instance available for {}", serviceId);
			throw new NotFoundException("No instance available for " + serviceId);
		}

		// 获取请求version，无则随机返回可用实例
		String reqVersion = request.getHeaders().getFirst(VERSION);
		if (StrUtil.isBlank(reqVersion)) {
			return instances.get(RandomUtil.randomInt(instances.size()));
		}

		// 遍历可以实例元数据，若匹配则返回此实例
		for (ServiceInstance instance : instances) {
			Map<String, String> metadata = instance.getMetadata();
				String targetVersion = MapUtil.getStr(metadata, VERSION);
			if (reqVersion.equalsIgnoreCase(targetVersion)) {
				log.info("gray requst match success :{} {}", reqVersion, instance);
				return instance;
			}
		}

		//若上面无匹配version，则优先匹配version 为 default 的实例，便于开发环境设置共通机器
		for (ServiceInstance instance : instances) {
			Map<String, String> metadata = instance.getMetadata();
			String targetVersion = MapUtil.getStr(metadata, VERSION);
			if ("default".equalsIgnoreCase(targetVersion)) {
				log.info("gray requst match default success :{} {}", reqVersion, instance);
				return instance;
			}
		}
		return instances.get(RandomUtil.randomInt(instances.size()));
	}
}
