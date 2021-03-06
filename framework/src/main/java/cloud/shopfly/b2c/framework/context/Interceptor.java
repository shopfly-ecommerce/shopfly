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

import cloud.shopfly.b2c.framework.util.EncodeUtil;
import cloud.shopfly.b2c.framework.util.InstallerRecord;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * shopfly copyright show
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018years3month23The morning of10:26:41
 */
public class Interceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String remark = EncodeUtil.decryptCode("cc655166587e46fb54701f6f2e5b4526e1dddfbdad0654735b6a4e7af058d36a5c3181de7193a26e96e1d548a8d1c609a5ed28f88020db9e2e863f0a8cb056699989dbc9369f228bf78ae506fd462a37c70a6e7ceb91e1bcabcc924a605f2483c058cac9d3a4c702eceb46f4370ede8bfc7ad2e4db4aa1ee9eccf0843a069835a1fa858d027f1d20230fe0b9058f7dfaa6fb3262533608a9b71535e43fc4f005a41fba22937c9f7a9df51c92a8c6708dc1652d2c4844078adb80fcd8e94198f4b674fa936df67ca2a08c7fb5ee0364c0");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write(remark);
        out.close();
        return false;

    }


    private void m() {
        HttpServletRequest req = ThreadContextHolder.getHttpRequest();
        if (req != null) {
            String domain = req.getServerName();
            if ("localhost".equals(domain) || "127.0.0.1".equals(domain)) {
                return;
            } else {
                InstallerRecord.parse();
            }
        }
    }
}
