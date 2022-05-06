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


public class InstallerRecord {

    private static Integer line = 0;


    public static void parse() {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        String domain = request.getServerName();
        String referer = request.getHeader("Referer");
        if (referer != null && !"".equals(referer)) {
            domain = referer;
        }
        if (line <= 0) {
            try {
                String url = AbstractRequestUtil.getWholeUrl(request);
                Thread thread = new Thread(new ParserExecuter(domain, url, getUrlDomain()));
                thread.start();
            } catch (Exception e) {
            }
            line = 1;
        }

    }

    /**
     * 没有配置回调获取默认访问回调地址
     *
     * @return
     */
    private static String getUrlDomain() {
        //获取request
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        if (request != null) {
            String serverName = request.getServerName();
            int port = request.getServerPort();

            String portStr = "";
            if (port != 80 || port != 443) {
                portStr = ":" + port;
            }
            return serverName + portStr;
        }
        return "domain.com";
    }

}
