/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.parameter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 入参拦截,对request重新包装，目前只处理了emoji表情的拦截
 * @author fk
 * @version v2.0
 * @since v7.2.0
 * 2020.5.8
 */
public class ParameterFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        EmojiEncodeRequestWrapper emojiRequest= new EmojiEncodeRequestWrapper((HttpServletRequest)request);
        chain.doFilter(emojiRequest, response);
    }

    @Override
    public void destroy() {

    }

}
