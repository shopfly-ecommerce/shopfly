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
package cloud.shopfly.b2c.framework.database;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * Database operation support interface
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface DaoSupport {

	/**
	 * performsqlstatements
	 * @param sql	sqlstatements
	 * @param args	Parameter set
	 * @return
	 */
	int execute(String sql, Object... args) ;
	
	/**
	 * Query a single result set<br/>
	 * And turn the results into<code>int</code>Return type
	 * @param sql Of the querysqlStatement to determine that the result is a row, column, and alphanumeric
	 * @param args The correspondingsqlParameter values in the statement
	 * @return
	 */
	Integer queryForInt(String sql, Object... args);
	
	/**
	 * Query a single result set<br/>
	 * And turn the results into<code>long</code>Return type
	 * @param sql Of the querysqlStatement to ensure that the result is one-row, one-column and alphanumeric
	 * @param args The correspondingsqlParameter values in the statement
	 * @return
	 */
	Long queryForLong(String sql, Object... args);
	
	
	/**
	 * Query a single result set<br/>
	 * And turn the results into<code>float</code>Return type
	 * @param sql Of the querysqlStatement to ensure that the result is one-row, one-column and alphanumeric
	 * @param args The correspondingsqlParameter values in the statement
	 * @return
	 */
	Float queryForFloat(String sql, Object... args);
	
	
	/**
	 * Query a single result set<br/>
	 * And turn the results into<code>dobule</code>Return type
	 * @param sql Of the querysqlStatement to ensure that the result is one-row, one-column and alphanumeric
	 * @param args The correspondingsqlParameter values in the statement
	 * @return
	 */
	Double queryForDouble(String sql, Object... args);
	
	
	
	
	/**
	 * The queryString The results of
	 * @param sql The query
	 * @param args parameter
	 * @return StringThe results of the type
	 */
	String queryForString(String sql, Object... args);
	
	/**
	 * Query a single result set<br/>
	 * And turn the results into<code>Map</code>The object returned
	 * @param sql Of the querysqlstatements
	 * @param args The correspondingsqlParameter values in the statement
	 * @return Columns in the result setkeyAnd has a value ofvaluethe<code>Map</code>
	 */
	@SuppressWarnings("unchecked")
	Map queryForMap(String sql, Object... args) ;
	
	/**
	 * Query multi-row result sets<br/>
	 * And turn the results into<code>List<Map></code>
	 * @param sql Of the querysqlstatements
	 * @param args The correspondingsqlParameter values in the statement
	 * @return  The elements in the list are<code>Map</code>the<code>List</code>,<br/>Mapstructure：以结果集中the列为keyAnd has a value ofvalue,
	 */
	@SuppressWarnings("unchecked")
	List  queryForList(String sql, Object... args);
	
	
	
	/**
	 * Query multi-row result sets<br/>
	 * And turn the results into<code>List<T></code>
	 * @param sql Of the querysqlstatements
	 * @param clazz <code><T></code>theClassobject
	 * @param args The correspondingsqlParameter values in the statement
	 * @return  The elements in the list are<code>T</code>the<code>List</code>
	 */
	<T> List<T> queryForList(String sql, Class<T> clazz, Object... args);


	/**
	 * userowmapperQuery multi-row result sets<br/>
	 * And turn the results into<code>List<T></code>
	 * @param sql Of the querysqlstatements
	 * @param rowMapper mapper
	 * @param args  Query parameters
	 * @param <T>   The type of the element in the list
	 * @return
	 */
	<T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args);


	/**
	 * Paging query multi-row result sets<br/>
	 * @param sql Of the querysqlstatements
	 * @param pageNo The start page of the query
	 * @param pageSize  Number each page
	 * @param args  The correspondingsqlParameter values in the statement
	 * @return The elements in the list are<code>Map</code>the<code>List</code>,<br/>Mapstructure：以结果集中the列为keyAnd has a value ofvalue,
	 */
	@SuppressWarnings("unchecked")
	<T> List<Map> queryForListPage(String sql, int pageNo, int pageSize, Object... args);
	

	/**
	 * Paging query
	 * @param sql  Of the querysqlstatements
	 * @param pageNo The start page of the query
	 * @param pageSize  Number each page
	 * @param args  The correspondingsqlParameter values in the statement
	 * @return Paging result set object
	 */
	Page queryForPage(String sql, int pageNo, int pageSize, Object... args) ;
	
	/**
	 * Paging query
	 * @param sql  Of the querysqlstatements
	 * @param countSql Used to query the total number of recordssqlstatements
	 * @param pageNo The start page of the query
	 * @param pageSize  Number each page
	 * @param args  The correspondingsqlParameter values in the statement
	 * @return Paging result set object
	 */
	Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Object... args);
	
	
	/**
	 * Paging query
	 * @param sql Of the querysqlstatements
	 * @param pageNo The start page of the query
	 * @param pageSize Number each page
	 * @param clazz  <code><T></code>theClassobject
	 * @param args The correspondingsqlParameter values in the statement
	 * @return
	 */
	<T> Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args);
	
	/**
	 * Update the data
	 * @param table The name of the table
	 * @param fields field-valueMap
	 * @param where Update the condition(field-valueMap)
	 * @return
	 */
	int update(String table, Map fields, Map<String,?> where);

	/**
	 * Batch Update Data
	 * @param sql To perform thesql
	 * @param batchArgs Corresponding parameters
	 * @return
	 */
	int[] batchUpdate(String sql,List<Object[]> batchArgs);

	/**
	 * Batch Update Data
	 * @param sql To perform thesqlAn array of
	 * @return
	 */
	int[] batchUpdate(String... sql);


	/**
	 * Update the data
	 * @param table The name of the table
	 * @param po To update the object, ensure that the property name of the object corresponds to the field name
	 * @param where Update the condition(field-valueMap)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int update(String table, Object po, Map<String,?> where);
	

	
	/**
	 * The new data
	 * @param table  The name of the table
	 * @param fields field-valueMap
	 */
	@SuppressWarnings("unchecked")
	void insert(String table, Map fields);
		
	/**
	 * The new data
	 * @param table The name of the table
	 * @param po To add an object, ensure that the attribute name of the object corresponds to the field name
	 */
	void insert(String table, Object po);

	/**
	 * Reads the last inserted primary keyid
	 * @param table	The name of the table
	 * @return
	 */
	int getLastId(String table);


	/**
	 * Execute pagedsqlstatements
	 * @param sql	sqlstatements
	 * @param page	Number of pages
	 * @param pageSize	A number of
	 * @return
	 */
	String buildPageSql(String sql, int page, int pageSize);

	/**
	 * The new data
	 * @param t  DOobject
	 * @param <T>
	 */
	<T> void insert(T t);

	/**
	 * Modify the data
	 * @param model	DOobject
	 * @param id	A primary keyID
	 * @param <T>
	 */
	<T> void update(T model,Integer id);

	/**
	 * Delete the data
	 * @param clazz	DOThe objectsclass
	 * @param id	A primary keyID
	 * @param <T>
	 */
	<T> void delete(Class<T> clazz,Integer id);

	/**
	 * Query a row of data
	 * @param clazz	DOThe objectsclass
	 * @param id	A primary keyID
	 * @param <T>
	 * @return
	 */
	<T> T queryForObject(Class<T> clazz,Integer id);


	/**
	 * Querying an object
	 * @param sql The querysql
	 * @param args  sqlQuery parameters in
	 * @param clazz Type of object
	 * @return Object after data transfer
	 */
	<T> T queryForObject(String sql,Class<T> clazz,Object... args);
}
