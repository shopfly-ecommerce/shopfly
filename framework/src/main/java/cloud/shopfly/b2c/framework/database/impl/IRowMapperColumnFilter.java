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
import java.sql.SQLException;
import java.util.Map;

/**
 * 数据库结果集过滤器
 * 可对RowMapper的结果某或多列进行过滤 
 * @author kingapex
 *
 */
public interface IRowMapperColumnFilter {
	
	
	/**
	 * 对结果集的行进行过滤
	 * @param colValues 结果集一行的map
	 * @param rs 结果集
	 * @throws  SQLException 可能的sql异常
	 */
    void filter(Map colValues, ResultSet rs) throws SQLException;
	
	
}
