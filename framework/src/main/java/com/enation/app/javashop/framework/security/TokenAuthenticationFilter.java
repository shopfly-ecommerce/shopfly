/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.security;

import com.enation.app.javashop.framework.security.impl.AbstractAuthenticationService;
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