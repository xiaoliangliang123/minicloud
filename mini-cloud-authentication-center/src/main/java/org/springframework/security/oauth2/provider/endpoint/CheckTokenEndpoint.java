/*
 * Copyright 2009-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.provider.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller which decodes access tokens for clients who are not able to do so (or where opaque token values are used).
 *
 * @author alan.wang
 */
@Slf4j
@FrameworkEndpoint
public class CheckTokenEndpoint {

    private Map<String,OAuth2AccessToken> accessTokenmap = new HashMap();
    private Map<String,OAuth2Authentication> oAuth2Authenticationmap = new HashMap();

    private ResourceServerTokenServices resourceServerTokenServices;

    private AccessTokenConverter accessTokenConverter = new CheckTokenAccessTokenConverter();

    protected final Log logger = LogFactory.getLog(getClass());

    private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    public CheckTokenEndpoint(ResourceServerTokenServices resourceServerTokenServices) {
        this.resourceServerTokenServices = resourceServerTokenServices;
    }

    /**
     * @param exceptionTranslator the exception translator to set
     */
    public void setExceptionTranslator(WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    /**
     * @param accessTokenConverter the accessTokenConverter to set
     */
    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.accessTokenConverter = accessTokenConverter;
    }

    @RequestMapping(value = "/oauth/check_token")
    @ResponseBody
    public Map<String, ?> checkToken(@RequestParam("token") String value) {

        long start =  System.currentTimeMillis();
        long readAccessTokenStart =  System.currentTimeMillis();
        OAuth2AccessToken token = null;
        OAuth2Authentication authentication = null;
//        if(accessTokenmap.containsKey(value)){
//            token = accessTokenmap.get(value);
//        }else {
//             token = resourceServerTokenServices.readAccessToken(value);
//            accessTokenmap.put(value,token);
//        }
        token = resourceServerTokenServices.readAccessToken(value);
        long readAccessTokenEnd =  System.currentTimeMillis();

        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        long loadAuthenticationTokenStart =  System.currentTimeMillis();

//        if(oAuth2Authenticationmap.containsKey(token.getValue())){
//            authentication = oAuth2Authenticationmap.get(token.getValue());
//        }else {
//            authentication = resourceServerTokenServices.loadAuthentication(token.getValue());
//            oAuth2Authenticationmap.put(token.getValue(),authentication);
//        }
        authentication = resourceServerTokenServices.loadAuthentication(token.getValue());
        long loadAuthenticationTokenEnd =  System.currentTimeMillis();


        long convertAccessTokenStart =  System.currentTimeMillis();
        Map<String, ?> map =  accessTokenConverter.convertAccessToken(token, authentication);
        long convertAccessTokenEnd =  System.currentTimeMillis();
        long end =  System.currentTimeMillis();
        log.warn("CheckTokenEndpoint readAccessToken use:{},loadAuthenticationToken ues:{},convertAccessToken use:{}, total use:{}",(readAccessTokenEnd-readAccessTokenStart),(loadAuthenticationTokenEnd-loadAuthenticationTokenStart),(convertAccessTokenEnd-convertAccessTokenStart),(end-start));
        return map;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        // This isn't an oauth resource, so we don't want to send an
        // unauthorized code here. The client has already authenticated
        // successfully with basic auth and should just
        // get back the invalid token error.
        @SuppressWarnings("serial")
        InvalidTokenException e400 = new InvalidTokenException(e.getMessage()) {
            @Override
            public int getHttpErrorCode() {
                return 400;
            }
        };
        return exceptionTranslator.translate(e400);
    }

    static class CheckTokenAccessTokenConverter implements AccessTokenConverter {
        private final AccessTokenConverter accessTokenConverter;

        CheckTokenAccessTokenConverter() {
            this(new DefaultAccessTokenConverter());
        }

        CheckTokenAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
            this.accessTokenConverter = accessTokenConverter;
        }

        @Override
        public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
            Map<String, Object> claims = (Map<String, Object>) this.accessTokenConverter.convertAccessToken(token, authentication);

            // gh-1070
            claims.put("active", true);		// Always true if token exists and not expired

            return claims;
        }

        @Override
        public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
            return this.accessTokenConverter.extractAccessToken(value, map);
        }

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            return this.accessTokenConverter.extractAuthentication(map);
        }
    }
}
