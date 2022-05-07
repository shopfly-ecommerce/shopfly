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

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.client.system.RoleClient;
import cloud.shopfly.b2c.framework.cache.Cache;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Authority data source provider<br/>
 * Provide the set of roles required for this resource
 * Created by kingapex on 2018/3/27.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/27
 */
public class SellerSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {

    private RoleClient roleClient;

    private Cache cache;

    public SellerSecurityMetadataSource(RoleClient roleClient, Cache cache) {
        this.roleClient = roleClient;
        this.cache = cache;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();

        return this.buildAttributes(url);

    }


    private Collection<ConfigAttribute> buildAttributes(String url) {
        List<String> roleList = new ArrayList<>();

        // Obtain the menu permission corresponding to each role from the cache. If the menu permission cannot be obtained from the cache, it is obtained from the database and stored in the cache.
        Map<String, List<String>> roleMap = (Map<String, List<String>>) cache.get(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        if (roleMap == null) {
            roleMap = roleClient.getRoleMap();
        }

        if (roleMap != null) {
            for (String role : roleMap.keySet()) {
                List<String> urlList = roleMap.get(role);
                if (matchUrl(urlList, url)) {
                    roleList.add("ROLE_" + role);
                }
            }
        }

        if (roleList.isEmpty()) {
            // There is no match, the default is super administrator access
            return SecurityConfig.createList("ROLE_SUPER_ADMIN");

        } else {
            return SecurityConfig.createList(roleList.toArray(new String[roleList.size()]));
        }

    }


    /**
     * Take a look at alist Is matched to aurl
     *
     * @param patternList A containantThe expression of thelist
     * @param url         To match theUrl
     * @return Whether there is a match for thisurlThe expression of, Returns atrue
     */
    private boolean matchUrl(List<String> patternList, String url) {
        // Traverse permission
        for (String expression : patternList) {

            // Match the permissions
            boolean isMatch = Pattern.matches(expression, url);

            if (isMatch) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
