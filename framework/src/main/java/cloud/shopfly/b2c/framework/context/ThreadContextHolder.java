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

 

/**
 *  withThreadLocalTo storeSession,In order to achieveSession any where 
 * @author kingapex
 * <p>2009-12-17 In the afternoon03:10:09</p>
 * @version 1.1
 * newrequest any where
 */
public class ThreadContextHolder  {

	private static ThreadLocal<HttpServletRequest> requestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();


	public static void setHttpRequest(HttpServletRequest request){
		
		requestThreadLocalHolder.set(request);
	}

	public static void setHttpResponse(HttpServletResponse response){
		responseThreadLocalHolder.set(response);
	}

	
	public static void remove(){
		requestThreadLocalHolder.remove();
		responseThreadLocalHolder.remove();
	}
	
	

	public static HttpServletResponse getHttpResponse(){
		
		return responseThreadLocalHolder.get();
	}
	public static HttpServletRequest getHttpRequest(){
		return  requestThreadLocalHolder.get();
	}


	
}
