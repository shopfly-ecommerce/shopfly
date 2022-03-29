/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.util;

import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 参数工具类
 * @author fk
 * @version v1.0
 * 2017年4月19日 下午4:40:30
 */
public class ParamsUtils {
	
	
	/**
	 * 将参数mapl转为url字串
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
	 * 由request中获取参数map
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
	 * 由request中根据prop参数获取属性数组<br>
	 * 由\@分割的
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
	 * 在原有的url基础上根据参数名和值生成新的属性url<br>
	 * 如原url为 search.html?cat=1&prop=p1_1，生成新的url:search.html?cat=1&prop=p1_1@name_value
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
