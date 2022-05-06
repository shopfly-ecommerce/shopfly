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
package cloud.shopfly.b2c.framework.database.impl;

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
