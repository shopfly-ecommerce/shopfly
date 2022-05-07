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
package cloud.shopfly.b2c.core.base;

import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Domain name Management
 *
 * @author zh
 * @version v7.0
 * @date 19/4/29 In the afternoon4:05
 * @since v7.0
 */
@Component
public class DomainHelper {

    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";
    /**
     * Buyer sideapiThe prefix
     */
    private static final String BUYER_API = "api.buyer.";


    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    private DomainSettings domainSettings;

    /**
     * Obtain the domain name of the buyer
     *
     * @return Buyer side domain name
     */
    public String getBuyerDomain() {
        String buyerDomain = domainSettings.getBuyerPc();
        // Check whether the network protocol is carried
        if (buyerDomain.indexOf("http") != -1) {
            return buyerDomain;
        }
        return shopflyConfig.getScheme() + buyerDomain;

    }

    /**
     * Get buyer sideWAPThe domain name
     *
     * @return Buyer sideWAPThe domain name
     */
    public String getMobileDomain() {
        String mobileDomain = domainSettings.getBuyerWap();
        // Check whether the network protocol is carried
        if (mobileDomain.indexOf("http") != -1) {
            return mobileDomain;
        }
        return shopflyConfig.getScheme() + mobileDomain;

    }

    /**
     * Gets the current primary domain name
     *
     * @return The main domain name
     */
    public String getTopDomain() {
        // Gets the current access domain name
        String result = getUrlDomain();
        Pattern pattern = Pattern.compile(RE_TOP, Pattern.CASE_INSENSITIVE);
        try {
            Matcher matcher = pattern.matcher(getUrlDomain());
            matcher.find();
            result = matcher.group();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Gets the full callback domain name
     * Train of thought：If the domain name contains a protocol, the domain name is returned. If the domain name contains a protocol, the domain name is returned
     *
     * @return Complete the domain name
     */
    public String getCallback() {
        String callback = domainSettings.getCallback();
        // If the domain name is not configured, the current accessed domain name is read
        String buyer = BUYER_API;
        if (StringUtil.isEmpty(callback)) {
            // Verify that the address can be obtained from Reques, read it directly if it can be obtained, and concatenate the address according to the established rule if it cannot be obtained
            if (StringUtil.isEmpty(getUrlDomain())) {
                callback = buyer + getTopDomain();
            } else {
                callback = getUrlDomain();
            }
        }
        // Return if the domain name contains a protocol
        if (callback.indexOf("http") != -1) {
            return callback;
        }
        return shopflyConfig.getScheme() + callback;
    }

    /**
     * The callback is not configured to get the default access callback address
     *
     * @return
     */
    private static String getUrlDomain() {
        // For the request
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        if (request != null) {
            String serverName = request.getServerName();
            int port = request.getServerPort();

            String portStr = "";
            if (port != 80) {
                portStr = ":" + port;
            }
            String contextPath = request.getContextPath();
            if ("/".equals(contextPath)) {
                contextPath = "";
            }
            return serverName + portStr + contextPath;
        }
        return "domain.com";
    }

}
