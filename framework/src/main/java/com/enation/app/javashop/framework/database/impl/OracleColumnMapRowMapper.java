/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.database.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 小写key的columnrowmapper
 *  本类覆写了spring 的ColumnMapRowMapper，将字段名全部小写
 * @author kingapex
 * 2010-6-13上午11:01:34
 */
public class OracleColumnMapRowMapper extends ColumnMapRowMapper {

	@Override
	public Map<String, Object>  mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = createColumnMap(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
			key = key.toLowerCase();
			Object obj = null;
			String typename= rsmd.getColumnTypeName(i);
			if("NUMBER".equals(typename)){
				int scale = rsmd.getScale(i);
				int precision = rsmd.getPrecision(i);
				if(scale == 0){
					if(precision<=10) {
						obj = rs.getInt(i);
					}
					else {
						obj = rs.getLong(i);
					}
				}else if(scale>0){
					obj = rs.getDouble(i);
				}else {
					obj = rs.getLong(i);
				}
			}else{
				obj = getColumnValue(rs, i);
			}
 
			mapOfColValues.put(key, obj);
		}
		return mapOfColValues;
	}
}
