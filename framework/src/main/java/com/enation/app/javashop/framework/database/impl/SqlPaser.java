/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql解析器
 * @author kingapex
 *
 */
public class SqlPaser {
	
	
	public static String insertSelectField(String field,String sql){
		sql = "select " + field+","+sql.substring(6, sql.length());
		return sql;
	}
	
	
	/**
	 * 从一个sql语句中找到order by 子句
	 * @param sql
	 * @return
	 */
	public static String findOrderStr(String sql ){

		String pattern = "(order\\s*by[\\w|\\W|\\s|\\S]*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(sql);

		if (m.find()) {
			return m.group();
		 
		} 
		return null;
	}
	
	
	public static void main(String[] args){
		String sql ="select * from abc where 12=12 order by id asc ";
	}
}
