/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.security.impl;

import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.security.AuthenticationService;
import cloud.shopfly.b2c.framework.security.TokenManager;
import cloud.shopfly.b2c.framework.security.model.Role;
import cloud.shopfly.b2c.framework.util.StringUtil;

import cloud.shopfly.b2c.framework.security.model.TokenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Common security configuration
 * 7.2.0Discard the replay attack judgment
 * v2.0: To optimize thetokenParse, using unifiedtokenParser
 *
 * @author zh
 * @version v2.0
 * @date 18/8/7 In the afternoon8:23
 * @since v7.0
 */
public abstract class AbstractAuthenticationService implements AuthenticationService {

    @Autowired
    protected TokenManager tokenManager;


    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * Singleton modecache
     */
    private static Cache<String, Integer> cache;


    @Autowired
    private ShopflyConfig shopflyConfig;


    /**
     * Authentication, obtained firsttokenAnd then according to thetokenTo the authentication
     * The production environment is made up ofnonceAnd time stamp, signature to gettoken
     * The development environment can pass directlytoken
     *
     * @param req
     */
    @Override
    public void auth(HttpServletRequest req) {
        String token = this.getToken(req);
        //System.out.println("token======="+token);
        if (StringUtil.notEmpty(token)) {
            Authentication authentication = getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
    }

    private String getKey(Role role, long uid) {

        return role.name() + "_" + uid;
    }

    /**
     * Parsing atoken
     * Subclasses need to addtokenParse your own sub-business permission model：Admin,seller buyer...
     *
     * @param token
     * @return
     */
    protected abstract AuthUser parseToken(String token);

    /**
     * According to atoken Generate the authorization
     *
     * @param token
     * @return authorization
     */
    protected Authentication getAuthentication(String token) {
        try {

            AuthUser user = parseToken(token);
            List<GrantedAuthority> auths = new ArrayList<>();

            List<String> roles = user.getRoles();

            for (String role : roles) {
                auths.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", null, auths);
            authentication.setDetails(user);

            return authentication;
        } catch (Exception e) {
            logger.error("Authentication exception", e);
            return new UsernamePasswordAuthenticationToken("anonymous", null);
        }
    }

    /**
     * To obtaintoken
     * 7.2.0Discard the replay attack judgment
     *
     * @param req
     * @return
     */
    protected String getToken(HttpServletRequest req) {

        String token = req.getHeader(TokenConstant.HEADER_STRING);
        if (StringUtil.notEmpty(token)) {
            token = token.replaceAll(TokenConstant.TOKEN_PREFIX, "").trim();
        }

        return token;
    }

    private static final Object lock = new Object();

    /**
     * Get local cache<br/>
     * Used to record disabled users<br/>
     * This cachekeyfor：role+The userid, such as： admin_1
     * valuefor：1It indicates that the user is disabled
     *
     * @return
     */
    protected Cache<String, Integer> getCache() {

        if (cache != null) {
            return cache;
        }
        synchronized (lock) {
            if (cache != null) {
                return cache;
            }
            // The cache time is the session validity period plus one minute
            // If a user is disabled, the session timeout cache is not needed:
            // Because he needs to log in again to be detected as invalid
            int sessionTimeout = shopflyConfig.getRefreshTokenTimeout() - shopflyConfig.getAccessTokenTimeout() + 60;

            // Use EhCache as the cache
            CachingProvider provider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
            CacheManager cacheManager = provider.getCacheManager();

            MutableConfiguration<String, Integer> configuration =
                    new MutableConfiguration<String, Integer>()
                            .setTypes(String.class, Integer.class)
                            .setStoreByValue(false)
                            .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sessionTimeout)));

            cache = cacheManager.createCache("userDisable", configuration);

            return cache;
        }
    }


}
