/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.database;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作支撑接口
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface DaoSupport {

	/**
	 * 执行sql语句
	 * @param sql	sql语句
	 * @param args	参数组
	 * @return
	 */
	int execute(String sql, Object... args) ;
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>int</code>型返回
	 * @param sql 查询的sql语句，确定结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	Integer queryForInt(String sql, Object... args);
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>long</code>型返回
	 * @param sql 查询的sql语句，确保结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	Long queryForLong(String sql, Object... args);
	
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>float</code>型返回
	 * @param sql 查询的sql语句，确保结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	Float queryForFloat(String sql, Object... args);
	
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>dobule</code>型返回
	 * @param sql 查询的sql语句，确保结果为一行一列，且为数字型
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	Double queryForDouble(String sql, Object... args);
	
	
	
	
	/**
	 * 查询String 结果
	 * @param sql 查询语句
	 * @param args 参数 
	 * @return String型的结果
	 */
	String queryForString(String sql, Object... args);
	
	/**
	 * 查询单一结果集<br/>
	 * 并将结果转为<code>Map</code>对象返回
	 * @param sql 查询的sql语句
	 * @param args 对应sql语句中的参数值
	 * @return 以结果集中的列为key，值为value的<code>Map</code>
	 */
	@SuppressWarnings("unchecked")
	Map queryForMap(String sql, Object... args) ;
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<Map></code>
	 * @param sql 查询的sql语句
	 * @param args 对应sql语句中的参数值
	 * @return  列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	List  queryForList(String sql, Object... args);
	
	
	
	/**
	 * 查询多行结果集<br/>
	 * 并将结果转为<code>List<T></code>
	 * @param sql 查询的sql语句
	 * @param clazz <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return  列表中元素为<code>T</code>的<code>List</code>
	 */
	<T> List<T> queryForList(String sql, Class<T> clazz, Object... args);


	/**
	 * 使用rowmapper查询多行结果集<br/>
	 * 并将结果转为<code>List<T></code>
	 * @param sql 查询的sql语句
	 * @param rowMapper mapper
	 * @param args  查询参数
	 * @param <T>   列表中元素类型
	 * @return
	 */
	<T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args);


	/**
	 * 分页查询多行结果集<br/>
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  对应sql语句中的参数值
	 * @return 列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
	 */
	@SuppressWarnings("unchecked")
	<T> List<Map> queryForListPage(String sql, int pageNo, int pageSize, Object... args);
	

	/**
	 * 分页查询
	 * @param sql  查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	Page queryForPage(String sql, int pageNo, int pageSize, Object... args) ;
	
	/**
	 * 分页查询
	 * @param sql  查询的sql语句
	 * @param countSql 用于查询总记录数的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize  每页数量
	 * @param args  对应sql语句中的参数值
	 * @return 分页结果集对象
	 */
	Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Object... args);
	
	
	/**
	 * 分页查询
	 * @param sql 查询的sql语句
	 * @param pageNo 查询的起始页
	 * @param pageSize 每页数量
	 * @param clazz  <code><T></code>的Class对象
	 * @param args 对应sql语句中的参数值
	 * @return
	 */
	<T> Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args);
	
	/**
	 * 更新数据
	 * @param table 表名
	 * @param fields 字段-值Map
	 * @param where 更新条件(字段-值Map)
	 * @return
	 */
	int update(String table, Map fields, Map<String,?> where);

	/**
	 * 批量更新数据
	 * @param sql 要执行的sql
	 * @param batchArgs 相应的参数
	 * @return
	 */
	int[] batchUpdate(String sql,List<Object[]> batchArgs);

	/**
	 * 批量更新数据
	 * @param sql 要执行的sql数组
	 * @return
	 */
	int[] batchUpdate(String... sql);


	/**
	 * 更新数据
	 * @param table 表名
	 * @param po 要更新的对象，保证对象的属性名和字段名对应
	 * @param where 更新条件(字段-值Map)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int update(String table, Object po, Map<String,?> where);
	

	
	/**
	 * 新增数据
	 * @param table  表名
	 * @param fields 字段-值Map
	 */
	@SuppressWarnings("unchecked")
	void insert(String table, Map fields);
		
	/**
	 * 新增数据
	 * @param table 表名
	 * @param po 要新增的对象，保证对象的属性名和字段名对应
	 */
	void insert(String table, Object po);

	/**
	 * 读取最后插入的主键id
	 * @param table	表名
	 * @return
	 */
	int getLastId(String table);


	/**
	 * 执行带分页的sql语句
	 * @param sql	sql语句
	 * @param page	页数
	 * @param pageSize	条数
	 * @return
	 */
	String buildPageSql(String sql, int page, int pageSize);

	/**
	 * 新增数据
	 * @param t  DO对象
	 * @param <T>
	 */
	<T> void insert(T t);

	/**
	 * 修改数据
	 * @param model	DO对象
	 * @param id	主键ID
	 * @param <T>
	 */
	<T> void update(T model,Integer id);

	/**
	 * 删除数据
	 * @param clazz	DO对象的class
	 * @param id	主键ID
	 * @param <T>
	 */
	<T> void delete(Class<T> clazz,Integer id);

	/**
	 * 查询一行数据
	 * @param clazz	DO对象的class
	 * @param id	主键ID
	 * @param <T>
	 * @return
	 */
	<T> T queryForObject(Class<T> clazz,Integer id);


	/**
	 * 查询一个对象
	 * @param sql 查询 sql
	 * @param args  sql中的查询 参数
	 * @param clazz 对象的类型
	 * @return 数据转后的对象
	 */
	<T> T queryForObject(String sql,Class<T> clazz,Object... args);
}
