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

package com.minicloud.common.balancer.fegin;


import cn.hutool.core.util.StrUtil;
import com.minicloud.common.constant.MiniCloudCommonConstant;
import com.minicloud.common.core.util.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.VERSION;


@Slf4j
public class MiniCloudFeignRequestInterceptor implements RequestInterceptor {


	@Override
	public void apply(RequestTemplate template) {
		String reqVersion = WebUtils.getRequest() != null
				? WebUtils.getRequest().getHeader(VERSION) : null;

		if (StrUtil.isNotBlank(reqVersion)) {
			log.debug("feign gray add header version :{}", reqVersion);
			template.header(VERSION, reqVersion);
		}
	}
}
