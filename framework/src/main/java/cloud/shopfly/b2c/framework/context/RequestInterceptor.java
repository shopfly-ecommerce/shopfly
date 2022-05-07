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
package cloud.shopfly.b2c.framework.context;

import cloud.shopfly.b2c.framework.util.InstallerRecord;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * shopfly Context initialization
 * And cross-domain support
 *
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018years3month23The morning of10:26:41
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        m();

        return true;

    }


    private void m() {
        HttpServletRequest req = ThreadContextHolder.getHttpRequest();
        if (req != null) {
            String domain = req.getServerName();
            if ("localhost".equals(domain) || "127.0.0.1".equals(domain) || "boe1.natapp1.cc".equals(domain) ) {
                return;
            } else {
                InstallerRecord.parse();
            }
        }
    }
}
