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
package cloud.shopfly.b2c.api.config.security.seller;

import cloud.shopfly.b2c.framework.context.AdminUserContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Management side access decision control<br/>
 * Check whether the permission is approved based on the permission provided by the permission data source
 *
 * @author kingapex
 * @version 1.0
 * @see SellerSecurityMetadataSource
 * Created by kingapex on 2018/3/27.
 * @since 7.0.0
 * 2018/3/27
 */
public class SellerAccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequestUrl();
        // Filter Swagger series
        AntPathMatcher matcher = new AntPathMatcher();
        Boolean result = matcher.match("/swagger-ui.html", url);
        result = result || matcher.match("/v2/api-docs**", url);
        result = result || matcher.match("/configuration/ui", url);
        result = result || matcher.match("/swagger-resources/**", url);
        result = result || matcher.match("/webjars/**", url);
        result = result || matcher.match("/configuration/security", url);
        if (result) {
            return;
        }
        // Filter background administrator login
        result = matcher.match("/seller/login**", url);
        result = result || matcher.match("/seller/systems/admin-users/token**", url);
        result = result || matcher.match("/seller/members/logout**", url);
        result = result || matcher.match("/seller/check/token**", url);
        if (result) {
            return;
        }
        if (AdminUserContext.getAdmin() != null) {
            boolean bool = this.adminRolesChecked(url);
            if (bool) {
                return;
            }
        }
        if (CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException("not allow");
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ca.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if ("ROLE_SUPER_ADMIN".equals(ga.getAuthority())) {
                    return;
                }
                if (ga.getAuthority().equals(needRole)) {
                    // If a role is matched, the system allows the user to pass
                    return;
                }
            }
        }
        // The URL has configuration permission. However, if the login user does not match the corresponding permission, the access is prohibited
        throw new AccessDeniedException("not allow");
    }

    /**
     * General permission control after login
     *
     * @param url apiThe path
     * @return
     */
    private boolean adminRolesChecked(String url) {
        // An exact match
        if ("/seller/index/page".equals(url)) {
            return true;
        }
        // Regular match
        boolean isMatch = Pattern.matches("/seller/systems/roles/[1-9].*", url);
        isMatch = isMatch || Pattern.matches("/regions/[1-9].*", url);
        isMatch = isMatch || Pattern.matches("/uploaders.*", url);
        isMatch = isMatch || Pattern.matches("/seller/index/page.*", url);
        isMatch = isMatch || Pattern.matches("/seller/settings/site", url);
        isMatch = isMatch || Pattern.matches("/seller/statistics/dashboard", url);
        isMatch = isMatch || Pattern.matches("/seller/token", url);
        return isMatch;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
