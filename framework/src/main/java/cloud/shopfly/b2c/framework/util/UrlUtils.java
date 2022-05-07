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
package cloud.shopfly.b2c.framework.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Online shop tools
 * 
 * @author apexking
 * 
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public   class UrlUtils {

	/**
	 * The name of the field used for the search
	 */
	private static String[] searchFields = new String[]{"cat","brand","price","sort","prop","nattr","page","tag","keyword"};
	
	
	public static String getParamStr(String servletPath){
		String pattern = "/search-(.*).html";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(servletPath);
		String str= servletPath;
		if (m.find()) {
			str = m.replaceAll("$1");
		}
		return str;
		
	}
	
	/**
	 * Combination arranges search terms and combines them into link addresses to return
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String addUrl(String url, String name, String value){
		String pattern = "/search-(.*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		String str= "";
		if (m.find()) {
			str = m.replaceAll("$1");
		}
		if(url != null && "/search".equals(url)){
			return "/search-" + name + "-" + value + ".html";
		}
		if(str == null || "".equals(str)){
			return url;
		}
		str = getExParamUrl(str, "page");
		// Composing new urls
		String newUrl = "";
		String temp = null;
		for(String field : searchFields){
			temp = getParamStringValue(str,field);
			if(temp != null){
				newUrl += "-" + field + "-" + temp;
			}
			if(name != null && name.equals(field)){
				newUrl += "-" + field + "-" + value;
			}
		}
		return "search" + newUrl + ".html";
	}
	
	
	/**
	 * Parse a search address string（In the online search page）, parses the parameter value according to the passed parameter number string as：<br/>
	 * Parameter number string：cat{5}keyword{test}<br/>
	 * thecatParameter values for:5,keywordParameter values for:test<br/>
	 * 
	 * @param param Search for an address string
	 * @param name name
	 * @return
	 */
	public static String getParamStringValue(String param, String name) {
//		String pattern = "(.*)" + name + "\\{(.[^\\{]*)\\}(.*)";
		param = getParamStr(param);
		String pattern = "(.*)" + name + "\\-(.[^\\-]*)(.*)";
		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(param);
		if (m.find()) {
			value = m.replaceAll("$2");
		}
		return value;
	} 
	
	
	
	public static int getParamInitValue(String param, String name) {
		String tempStr = getParamStringValue(param,name);
		
		int value = Integer.valueOf(tempStr);
		return value;
		
	}
	
	/**
	 * Parse a search address string（In the online search page）To get a string that excludes an argument
	 * @param url The search string
	 * @param name The string to exclude
	 * @return
	 */
	public static String getExParamUrl(String url,String name){
		String pattern = "(.*)" + name + "\\-(.[^\\-]*)(\\-|.*)(.*)";
		String value = "";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		if (m.find()) {
			value = m.replaceAll("$1$4");
		}else{
			value=url;
		}
		if(value != null){
			if(value.startsWith("-")){
				value = value.substring(1,value.length());
			}
			if(value.endsWith("-")){
				value = value.substring(0,value.length()-1);
			}
		}
		return value;
 
	}
	
	
	
	
	
	/**
	 * Operates on a search string：Adds parameter values to the string.<br/>
	 * Add a new parameter value on the basis of the original parameter value“,"Separated.
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String appendParamValue(String url,String name,String value){
		
		String oldValue= getParamStringValue(url, name);
		String newUrl =  getExParamUrl(url,name);
		
		if(oldValue!=null){
			// Special handling of attribute strings
			if("prop".equals(name)){ 
				oldValue= oldValue.replaceAll( value.split("_")[0] +"_(\\d+)", "");
				oldValue=oldValue.replace(",,", ",");
				if( oldValue.startsWith(",") ){
					oldValue  = oldValue.substring(1,oldValue.length());
				}
				
				if(oldValue.endsWith(",")){
					oldValue = oldValue.substring(0,oldValue.length()-1);
				}
				
			}
			
			if(!"".equals(oldValue)) {
				value = "," + value;
			}
			
		}else{
			oldValue="";
		}
		
//		new_url =new_url+name+"-"+old_value+value;
		newUrl = addUrl(newUrl,name,oldValue+value);
		
		return newUrl;
	}
	
	
	/**
	 * Resolves a pseudo static stateurlstring<br/>
	 * You get this pseudo-veinurlThe prefix of a string<br/>
	 * Such as：/goods_list_tag-tag_id{3}.html getgoods_list_tag<br/>
	 * /search-cat_id{3}page{1}.html getsearch
	 * @param url
	 * @return
	 */
	public static String getUrlPrefix(String url){
		String pattern = "/(.*)-(.*)";

		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		if (m.find()) {
			value = m.replaceAll("$1"); 
		}else{
			value=null;
		}
		return value;
	}
	
	
	/**
	 * Gets the attribute string after an attribute has been excluded
	 * @param index
	 * @param url
	 * @return
	 */
	public static String getPropExSelf(int index,String url){
		
		if(StringUtil.isEmpty(url)){
			return url;
		}
		String propstr = getParamStringValue(url, "prop");
		if(StringUtil.isEmpty(propstr)){ return url; }
		
		String newprostr ="";
		String[] propar = propstr.split(",");
		for(String prop:propar){
			String[] ar =prop.split("_");
			// Get rid of this property
			if( !ar[0].equals(""+index )){ 
				if(!"".equals(newprostr)) {
					newprostr += ",";
				}
				newprostr+=prop;
			}
		}
		if(!StringUtil.isEmpty(newprostr)){
			url =url.replaceAll(propstr, newprostr);
		}else{
			url = url.replaceAll("-prop-" + propstr, "");
		}
		return url;
		
	}
	
	public static void main(String[] args){
//		String str = UrlUtils.getParamStr("/search-cat-1.html");
//		//System.out.println(str);
//		//System.out.println( "9_1,8_2,2_1".replaceAll("(,|2_(\\d+)", "")  );
		String url  ="cat{102}prop{2_1,1_2,0_3}name{2010}page{9}";
		String newUrl = "cat-102-page-9-prop-2_1,1_2,0_3";
//		//System.out.println( getPropExSelf(2,url) );
//		//System.out.println(getExParamUrl(newUrl,"cat"));
//		//System.out.println(getExParamUrl(newUrl,"prop"));
//		//System.out.println(getExParamUrl(newUrl,"name"));
//		//System.out.println(getExParamUrl(newUrl,"page"));
//		url="search-cat{4}brand{13}prop{}.html";
//		//System.out.println( getExParamUrl(url, "prop") );
//		String pattern = "(.*)\\-(.[^\\-]*)";
//		String value = "";
//		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
//		Matcher m = p.matcher(newUrl);
//
//		if (m.find()) {
//			value = m.replaceAll("$1$2");
//			//System.out.println(value);
//		}
		String temp = "/search-cat-1";
		//System.out.println(addUrl(temp,"circlar","3"));
	}
}
