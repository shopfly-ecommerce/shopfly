/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * javashop 上下文初始化
 * 以及跨域的支持
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class JavashopRequestInterceptor extends HandlerInterceptorAdapter {


	/**
	 * 拦截request和response并放到上下文中
	 * @param request 要拦截的request
	 * @param response 要拦截的response
	 * @param handler spring 机制传递过来的
	 * @return 不中断，继续执行，返回为true
	 * @throws Exception
	 */
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

		//request 和response存到 上下文中
		ThreadContextHolder.setHttpResponse(response);
		ThreadContextHolder.setHttpRequest(request);

		return super.preHandle(request, response, handler);
	}


	/**
	 * 处理完成 从上下文中移除 request 和respseon
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		ThreadContextHolder.remove();

		super.afterCompletion(request, response, handler, ex);
	}
}
