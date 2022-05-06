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
