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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Exception format definition for validation failure
 * Created by kingapex on 2018/4/18.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/4/18
 */

@Configuration
public class AccessDeniedFormatConfig {



    /**
     * Defines the format for an authentication failure
     * @return
     */
    @Bean
   public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler(){

            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.setStatus(403);
                httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                httpServletResponse.getWriter().print("{\"code\":\"403\",\"message\":\"The login status is invalid\"}");
                httpServletResponse.getWriter().flush();
            }
        };
    }

    /**
     * Defines the format for an authentication failure
     * @return
     */

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException authException)
                    throws IOException, ServletException {
                httpServletResponse.setStatus(403);
                httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                httpServletResponse.getWriter().print("{\"code\":\"403\",\"message\":\"The login status is invalid\"}");
                httpServletResponse.getWriter().flush();
            }
        };
    }

}
