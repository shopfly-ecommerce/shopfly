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
package cloud.shopfly.b2c.core.goods.util;

import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Parameter tool class
 * @author fk
 * @version v1.0
 * 2017years4month19On the afternoon4:40:30
 */
public class ParamsUtils {
	
	
	/**
	 * The parametermapltourlstring
	 * @param params
	 * @return
	 */
	public static String paramsToUrlString(Map<String,String> params){
		
		StringBuffer url = new StringBuffer();
		Iterator<String> keyIter  = params.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value  = params.get(key);
			
			if(StringUtil.isEmpty(value)) {
				continue;
			}
			if(url.length()!=0){
				url.append("&");
			}
			url.append(key+"="+value);
			
			
		}
		
		return  url.toString() ;
	}
	
	
	/**
	 * byrequestTo obtain parameters frommap
	 * @return
	 */
	public static  Map<String,String> getReqParams(){
		
		Map<String,String> map = new HashMap<String,String>(16);
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			
			String name = paramNames.nextElement();
	
			String value = request.getParameter(name);
			map.put(name, value);
		}
		
		return map;
		
	}
	
	
	
	/**
	 * byrequestAccording to thepropParameter gets an array of properties<br>
	 * by\@Segmentation of
	 * @return
	 */
	public static  String[] getProps(){
		
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String prop = request.getParameter("prop");
		
		if(!StringUtil.isEmpty(prop)){
			String[] propArray = prop.split(Separator.SEPARATOR_PROP);
			return propArray;
		}
		
		return new String[]{};

	}

	/**
	 * In the originalurlNew attributes are generated based on parameter names and valuesurl<br>
	 * Such as the originalurlforsearch.html?cat=1&prop=p1_1, generate a newurl:search.html?cat=1&prop=p1_1@name_value
	 * @param name
	 * @param value
	 * @return
	 */
	public static String createPropUrl(String name,String value){
		Map<String,String> params = ParamsUtils.getReqParams();
		String param = params.get("prop");
		if(!StringUtil.isEmpty(param)){
			param = param + Separator.SEPARATOR_PROP;
		}else{
			param = "";
		}
		param = param + name + Separator.SEPARATOR_PROP_VLAUE + value;
		params.put("prop", param);
		return ParamsUtils.paramsToUrlString(params);
	}
	
}
