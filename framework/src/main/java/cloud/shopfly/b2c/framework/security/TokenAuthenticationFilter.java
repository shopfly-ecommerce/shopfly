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
package cloud.shopfly.b2c.framework.security;

import cloud.shopfly.b2c.framework.security.impl.AbstractAuthenticationService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by kingapex on 2018/3/10.
 * Token 验权filter
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/10
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

    private AbstractAuthenticationService authenticationService;

    public TokenAuthenticationFilter(AbstractAuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    /**
     * 拦截客户端发过来的token，进行鉴权
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        authenticationService.auth(req);

        filterChain.doFilter(request, response);
    }



}