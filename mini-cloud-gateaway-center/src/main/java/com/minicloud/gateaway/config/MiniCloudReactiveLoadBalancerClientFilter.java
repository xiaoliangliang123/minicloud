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

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Set;


@Slf4j
public class MiniCloudReactiveLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {
	private static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10150;
	private GatewayLoadBalancerProperties properties;
	private LoadBalancerClientFactory clientFactory;

	private MiniCloudLoadBalancer miniCloudLoadBalancer;




	public MiniCloudReactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory,GatewayLoadBalancerProperties properties,MiniCloudLoadBalancer miniCloudLoadBalancer) {
		super(null, properties);
		this.properties = properties;
		this.clientFactory = clientFactory;
		this.miniCloudLoadBalancer = miniCloudLoadBalancer;
	}


	@Override
	public int getOrder() {
		return LOAD_BALANCER_CLIENT_FILTER_ORDER;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		URI url = (URI)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
		String schemePrefix = (String)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
		if (url != null && ("lb".equals(url.getScheme()) || "lb".equals(schemePrefix))) {
			ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
			if (log.isTraceEnabled()) {
				log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
			}

			URI requestUri = (URI)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
			String serviceId = requestUri.getHost();
			ServerHttpRequest serverHttpRequest = exchange.getRequest();
			Set<LoadBalancerLifecycle> supportedLifecycleProcessors = LoadBalancerLifecycleValidator.getSupportedLifecycleProcessors(this.clientFactory.getInstances(serviceId, LoadBalancerLifecycle.class), RequestDataContext.class, ResponseData.class, ServiceInstance.class);
			DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest(new RequestDataContext(new RequestData(exchange.getRequest()), this.getHint(serviceId)));
			return this.choose(requestUri,serverHttpRequest).doOnNext((response) -> {
				if (!response.hasServer()) {
					supportedLifecycleProcessors.forEach((lifecycle) -> {
						lifecycle.onComplete(new CompletionContext(CompletionContext.Status.DISCARD, lbRequest, response));
					});
					throw NotFoundException.create(this.properties.isUse404(), "Unable to find instance for " + url.getHost());
				} else {
					ServiceInstance retrievedInstance = (ServiceInstance)response.getServer();
					URI uri = exchange.getRequest().getURI();
					String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
					if (schemePrefix != null) {
						overrideScheme = url.getScheme();
					}

					DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance, overrideScheme);
					URI requestUrl = this.reconstructURI(serviceInstance, uri);
					if (log.isTraceEnabled()) {
						log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
					}

					exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
					exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
					supportedLifecycleProcessors.forEach((lifecycle) -> {
						lifecycle.onStartRequest(lbRequest, response);
					});
				}
			}).then(chain.filter(exchange)).doOnError((throwable) -> {
				supportedLifecycleProcessors.forEach((lifecycle) -> {
					lifecycle.onComplete(new CompletionContext(CompletionContext.Status.FAILED, throwable, lbRequest, (Response)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR)));
				});
			}).doOnSuccess((aVoid) -> {
				supportedLifecycleProcessors.forEach((lifecycle) -> {
					lifecycle.onComplete(new CompletionContext(CompletionContext.Status.SUCCESS, lbRequest, (Response)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR), new ResponseData(exchange.getResponse(), new RequestData(exchange.getRequest()))));
				});
			});
		} else {
			return chain.filter(exchange);
		}
	}

	private String getHint(String serviceId) {
		LoadBalancerProperties loadBalancerProperties = this.clientFactory.getProperties(serviceId);
		Map<String, String> hints = loadBalancerProperties.getHint();
		String defaultHint = (String)hints.getOrDefault("default", "default");
		String hintPropertyValue = (String)hints.get(serviceId);
		return hintPropertyValue != null ? hintPropertyValue : defaultHint;
	}

	private Mono<Response<ServiceInstance>> choose(URI requestUri,ServerHttpRequest serverHttpRequest) {
		ServiceInstance serviceInstance = miniCloudLoadBalancer.choose(requestUri.getHost(),serverHttpRequest);
		return Mono.just(new DefaultResponse(serviceInstance));
	}
}
