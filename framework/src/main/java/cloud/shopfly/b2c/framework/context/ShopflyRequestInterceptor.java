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
 * shopfly Context initialization
 * And cross-domain support
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018years3month23The morning of10:26:41
 */
public class ShopflyRequestInterceptor extends HandlerInterceptorAdapter {


	/**
	 * interceptrequestandresponseAnd put it in context
	 * @param request To interceptrequest
	 * @param response To interceptresponse
	 * @param handler spring Its delivered by mechanism
	 * @return Continue without interruption, return totrue
	 * @throws Exception
	 */
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

		// Request and response are stored in the context
		ThreadContextHolder.setHttpResponse(response);
		ThreadContextHolder.setHttpRequest(request);

		return super.preHandle(request, response, handler);
	}


	/**
	 * The process is removed from the contextrequest andrespseon
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
