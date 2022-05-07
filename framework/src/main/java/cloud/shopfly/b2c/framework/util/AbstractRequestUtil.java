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
package cloud.shopfly.b2c.framework.util;

import cloud.shopfly.b2c.framework.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * httpRequestCommon Method tools
 *
 * @author kingapex
 * 2010-2-12In the morning11:34:48
 */
public abstract class AbstractRequestUtil {
    private AbstractRequestUtil() {
    }

    /**
     * willrequestIs converted toMap
     *
     * @param request
     * @return
     */
    public static Map<String, String> paramToMap(HttpServletRequest request) {

        Map<String, String> params = new HashMap<String, String>(16);
        Map rMap = request.getParameterMap();
        Iterator rIter = rMap.keySet().iterator();

        while (rIter.hasNext()) {
            Object key = rIter.next();
            String value = request.getParameter(key.toString());
            if (key == null || value == null) {
                continue;
            }
            params.put(key.toString(), value.toString());
        }

        return params;

    }

    /**
     * Get completeurl, including domain names and ports
     *
     * @return
     */
    public static String getWholeUrl(HttpServletRequest request) {
        String servername = request.getServerName();
        String path = request.getServletPath();
        int port = request.getServerPort();

        String portstr = "";
        if (port != 80) {
            portstr = ":" + port;
        }
        String contextPath = request.getContextPath();
        if ("/".equals(contextPath)) {
            contextPath = "";
        }


        String url = "http://" + servername + portstr + contextPath + "/" + path;

        return url;

    }

    public static String getRequestUrl(HttpServletRequest request) {
        String redirect = (String) request.getAttribute("redirect");
        if (redirect != null) {
            return redirect;
        }
        String pathInfo = (request).getPathInfo();
        String queryString = (request).getQueryString();

        String uri = (request).getServletPath();
        String ctx = request.getContextPath();
        ctx = "/".equals(ctx) ? "" : ctx;
        /*		uri = uri.startsWith("/")?uri.substring(1, uri.length()):uri;
         */
        if (uri == null) {
            uri = (request).getRequestURI();
            uri = uri.substring((request).getContextPath().length());
        }

        return ctx + uri + ((pathInfo == null) ? "" : pathInfo)
                + ((queryString == null) ? "" : ("?" + queryString));
    }

    /**
     * To obtainInteger The value of the
     *
     * @param request
     * @param name
     * @return If it doesnt returnnull
     */
    public static Integer getIntegerValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtil.isEmpty(value)) {
            return null;
        } else {
            return Integer.valueOf(value);
        }

    }


    public static Double getDoubleValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtil.isEmpty(value)) {
            return null;
        } else {
            return Double.valueOf(value);
        }

    }


    /**
     * To obtainintThe value of the
     *
     * @param request
     * @param name
     * @return If it doesnt return0
     */
    public static int getIntValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtil.isEmpty(value)) {
            return 0;
        } else {
            return Integer.valueOf(value);
        }
    }

    public static String getRequestMethod(HttpServletRequest request) {
        String method = request.getParameter("_method");
        method = method == null ? "get" : method;
        method = method.toUpperCase();
        return method;
    }


    /**
     * Check for mobile access
     *
     * @return trueAs afalseFor not mobile phone access
     */
    public static boolean isMobile() {

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        if (request == null) {
            return false;
        }
        String userAgentFrom = request.getHeader("user-agent");
        if (StringUtil.isEmpty(userAgentFrom)) {
            return false;
        }

        String userAgent = userAgentFrom.toLowerCase();

        return userAgent.contains("android") || userAgent.contains("iphone");

    }

}
