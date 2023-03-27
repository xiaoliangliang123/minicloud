/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minicloud.authentication.endpoint;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author alan.wang
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class MinicloudTokenEndpoint {

	private final ClientDetailsService clientDetailsService;

	private final TokenStore tokenStore;

	private final RedisTemplate redisTemplate;

	private final CacheManager cacheManager;



	/**
	 * 退出并删除token
	 * @param authHeader Authorization
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StrUtil.isBlank(authHeader)) {
			return ResponseEntity.ok().build();
		}

		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
		return removeToken(tokenValue);
	}

	/**
	 * 令牌管理调用
	 * @param token token
	 */
	@DeleteMapping("/{token}")
	public ResponseEntity<Boolean> removeToken(@PathVariable("token") String token) {
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
			return ResponseEntity.ok().build();
		}

		OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
		// 清空用户信息
		cacheManager.getCache("user_details").evict(auth2Authentication.getName());

		// 清空access token
		tokenStore.removeAccessToken(accessToken);

		// 清空 refresh token
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		tokenStore.removeRefreshToken(refreshToken);


		return ResponseEntity.ok().build();
	}


}
