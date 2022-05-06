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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * shopfly 上下文初始化
 * 以及跨域的支持
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class ShopflyRequestInterceptor extends HandlerInterceptorAdapter {


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
