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

package com.minicloud.common.balancer.config;


import com.minicloud.common.balancer.fegin.MiniCloudFeignRequestInterceptor;
import com.minicloud.common.balancer.rule.MiniCloudRibbonLoadBalancerRule;
import feign.RequestInterceptor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class MiniCloudRibbonLoadBalancerConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MiniCloudRibbonLoadBalancerRule minicloudRibbonLoadBalancerRule() {
		return new MiniCloudRibbonLoadBalancerRule();
	}

	@Bean
	public RequestInterceptor minicloudFeignRequestInterceptor() {
		return new MiniCloudFeignRequestInterceptor();
	}
}
