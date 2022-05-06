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

import cloud.shopfly.b2c.framework.database.*;
import cloud.shopfly.b2c.framework.util.ReflectionUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.database.*;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * jdbc数据库操作支撑实现类
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public class DaoSupportImpl implements DaoSupport {


    private JdbcTemplate jdbcTemplate;

	@Autowired
	private SqlMetaBuilder sqlMetaBuilder;

    /**
     * 日志记录
     */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * order by 语句正则
     */
    private  static final Pattern ORDER_PATTERN = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);

    /**
     * 删除select正则
     */
    private  static final Pattern REMOVE_SELECT_PATTERN  = Pattern.compile("\\(.*\\)", Pattern.CASE_INSENSITIVE);

	public DaoSupportImpl(){
	}

    /**
     * 实例化jdbcTemplate
     */
	public DaoSupportImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate= jdbcTemplate;
	}


	@Override
	public int execute(String sql, Object... args) {
		try {
			int rowNum = this.jdbcTemplate.update(sql, args);

			return rowNum;
		} catch (Exception e) {
			throw new DBRuntimeException(e, sql);
		}
	}


	@Override
	public int getLastId(String table) {
			return queryForInt("SELECT last_insert_id() as id");
	}

	@Override
	public void insert(String table, Map fields) {
		String sql = "";

		try {

			Assert.hasText(table, "表名不能为空");
			Assert.notEmpty(fields, "字段不能为空");
			table = quoteCol(table);

			Object[] cols = fields.keySet().toArray();
			Object[] values = new Object[cols.length];

			for (int i = 0; i < cols.length; i++) {
				if (fields.get(cols[i]) == null) {
					values[i] = null;
				} else {
					values[i] = fields.get(cols[i]).toString();
				}
				cols[i] = quoteCol(cols[i].toString());
			}

			sql = "INSERT INTO " + table + " (" + StringUtil.implode(", ", cols);

			sql = sql + ") VALUES (" + StringUtil.implodeValue(", ", values);

			sql = sql + ")";

			jdbcTemplate.update(sql, values);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new DBRuntimeException(e, sql);
		}
	}

	@Override
	public void insert(String table, Object po) {
		insert(table, ReflectionUtil.po2Map(po));
	}

	@Override
	public Integer queryForInt(String sql, Object... args) {
		try {
			Integer value = jdbcTemplate.queryForObject(sql, Integer.class, args);
			return  value==null?0:value;
		}catch(EmptyResultDataAccessException e){
			return 0;
		} catch (RuntimeException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Float queryForFloat(String sql, Object... args) {
		try {

			Float value = jdbcTemplate.queryForObject(sql, Float.class, args);
			return  value==null?0F:value;

		} catch(EmptyResultDataAccessException e){
			return 0F;
		} catch (RuntimeException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Long queryForLong(String sql, Object... args) {
		try {
			Long value = jdbcTemplate.queryForObject(sql, Long.class, args);
			return  value==null?0L:value;
		} catch(EmptyResultDataAccessException e){
			return 0L;
		} catch (RuntimeException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public Double queryForDouble(String sql, Object... args) {
		try {

			Double value = jdbcTemplate.queryForObject(sql, Double.class, args);
			return  value==null?0D:value;

		} catch(EmptyResultDataAccessException e){
			return 0D;
		} catch (RuntimeException e) {
			this.logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String queryForString(String sql, Object... args) {
		
		String s = "";
		try {
			s = this.jdbcTemplate.queryForObject(sql, String.class,args);
		}catch (EmptyResultDataAccessException e){
			return "";
		}catch (RuntimeException e) {
			if(logger.isDebugEnabled()){
				logger.debug("查询sql:["+sql+"]出错",e);
			}

		}
		return s;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryForList(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}


	@Override
	public <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) {

		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(clazz), args);

	}

	@Override
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args) {
		return jdbcTemplate.query(sql,rowMapper,args);
	}

	@Override
	public List queryForListPage(String sql, int pageNo, int pageSize, Object... args) {

		try {
			Assert.hasText(sql, "SQL语句不能为空");
			Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
			String listSql = this.buildPageSql(sql, pageNo, pageSize);
			return queryForList(listSql, args);
		} catch (Exception ex) {
			throw new DBRuntimeException(ex, sql);
		}

	}

	@Override
	public Map queryForMap(String sql, Object... args) {
		try {
			Map map = this.jdbcTemplate.queryForMap(sql, args);

				return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ObjectNotFoundException(ex, sql);
		}
	}

	@Override
	public Page queryForPage(String sql, int pageNo, int pageSize, Object... args) {
		String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
		return this.queryForPage(sql, countSql, pageNo, pageSize, args);
	}

	@Override
	public Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Object... args) {
		Assert.hasText(sql, "SQL语句不能为空");
		Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
		String listSql = buildPageSql(sql, pageNo, pageSize);

		List list = queryForList(listSql, args);
		Long totalCount = queryForLong(countSql, args);
		return new Page(pageNo, totalCount, pageSize, list);

	}
	@Override
	public <T> Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args) {

		Assert.hasText(sql, "SQL语句不能为空");
		Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
		String listSql = buildPageSql(sql, pageNo, pageSize);
		String countSql = "SELECT COUNT(*) " + removeSelect(removeOrders(sql));
		List<T> list = this.queryForList(listSql, clazz, args);
		Long totalCount = queryForLong(countSql, args);
		return new Page(pageNo, totalCount, pageSize, list);

	}

	@Override
	public int update(String table, Map fields, Map<String,?> where) {

		Assert.hasText(table, "表名不能为空");
		Assert.notEmpty(fields, "字段不能为空");
		Assert.notEmpty(where, "where条件不能为空");

		String whereSql = this.createWhereSql(where);

		// 字段名
		Object[] cols = fields.keySet().toArray();
		String fieldSql = "";

		for(int i=0;i<cols.length;i++){

			fieldSql+= cols[i]+"=?";
			 if(i!=cols.length-1){
				 fieldSql+=",";
			 }
		}

		// 字段值
		Object[] values  = ArrayUtils.addAll(fields.values().toArray(),where.values().toArray());

		String sql = "UPDATE " + table + " SET " +fieldSql  + " WHERE " + whereSql;

		return this.jdbcTemplate.update(sql, values);

	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public int[] batchUpdate(String... sql) {
		return jdbcTemplate.batchUpdate(sql);
	}

	@Override
	public int update(String table, Object po, Map<String,?> where) {

		return update(table, ReflectionUtil.po2Map(po), where);
	}


	@Override
	public String buildPageSql(String sql, int page, int pageSize) {

		String sqlStr = null;

		String dbType =  "mysql";

		//防止魔法值
		String mysqlStr = "mysql";
		String sqlserverStr = "sqlserver";
        String oracleStr = "oracle";

		if (mysqlStr.equals(dbType)) {
			sqlStr = sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
		} else if (oracleStr.equals(dbType)) {
			StringBuffer localSql = new StringBuffer("SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");
			localSql.append(sql);
			localSql.append(") t1) t2 WHERE t2.sn1 BETWEEN ");
			localSql.append((page - 1) * pageSize + 1);
			localSql.append(" AND ");
			localSql.append(page * pageSize);
			sqlStr = localSql.toString();
		} else if (sqlserverStr.equals(dbType)) {
			StringBuffer localSql = new StringBuffer();
			// 找到order by 子句
			String order = SqlPaser.findOrderStr(sql);

			// 剔除order by 子句
			if (order != null) {
				sql = removeOrders(sql);
			}
			else {
				// SQLServer分页必需有order by
				// 子句，如果默认语句不包含order by，
				// 自动以id降序，如果没有id字段会报错
				order = "order by id desc";

			}

			// 拼装分页sql
			localSql.append("select * from (");
			localSql.append(SqlPaser.insertSelectField("ROW_NUMBER() Over(" + order + ") as rowNum", sql));
			localSql.append(") tb where rowNum between ");
			localSql.append((page - 1) * pageSize + 1);
			localSql.append(" AND ");
			localSql.append(page * pageSize);

			return localSql.toString();

		}

		return sqlStr.toString();

	}

	/**
	 * 格式化列名 只适用于Mysql
	 *
	 * @param col
	 * @return
	 */
	private String quoteCol(String col) {
		if (col == null || "".equals(col)) {
			return "";
		} else {
			// if("2".equals(EopSetting.DBTYPE))//Oracle
			// return "\"" + col + "\"";
			// else if("1".equals(EopSetting.DBTYPE))//mysql
			// return "`" + col + "`";
			// else //mssql
			// return "[" + col + "]";
			return col;
		}
	}

	/**
	 * 格式化值 只适用于Mysql
	 *
	 * @param value
	 * @return
	 */
	private String quoteValue(String value) {
		if (value == null || "".equals(value)) {
			return "''";
		} else {
			return "'" + value.replaceAll("'", "''") + "'";
		}
	}

	private String getStr(int num, String str) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			sb.append(str);
		}
		return sb.toString();
	}





    @Override
	public <T> void insert(T t){
		DataMeta dataMeta  = this.sqlMetaBuilder.insert(t);
		String sql = dataMeta.getSql();
		Object[] param = dataMeta.getParamter();
		this.jdbcTemplate.update(sql,param);
	}


	@Override
	public <T> void update(T model,Integer id) {
		DataMeta dataMeta  = this.sqlMetaBuilder.update(model,id);
		String sql = dataMeta.getSql();
		Object[] param = dataMeta.getParamter();
		this.jdbcTemplate.update(sql,param);
	}


	@Override
	public <T> void delete(Class<T> clazz, Integer id) {
		String sql = this.sqlMetaBuilder.delete(clazz);
		Integer[] ids = {id};
		this.jdbcTemplate.update(sql,ids);
	}


	@Override
	public <T> T  queryForObject(Class<T> clazz, Integer id) {

		String sql = this.sqlMetaBuilder.queryForModel(clazz);
		Integer[] ids = {id};
		List<T> objList = this.queryForList(sql, clazz, ids);
		if (objList.isEmpty()) {
			return null;
		}
		return objList.get(0);
	}


	@Override
	public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
		List<T> objList = this.queryForList(sql, clazz, args);
		if (objList.isEmpty()) {
			return null;
		}
		return objList.get(0);
	}


    /**
     * 去除hql的order by 子句，用于pagedQuery.
     *
     */
    private String removeOrders(String hql) {
        Assert.hasText(hql,"hql must hast text");

        Matcher m = ORDER_PATTERN.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
     */
    private String removeSelect(String sql) {

        String groupBySql="group by";
        sql = sql.toLowerCase();
        if (sql.indexOf(groupBySql) != -1) {
            return " from (" + sql + ") temp_table";
        }

        // FIXME 当查询中含有函数，比如SUM(),替换SQL出错
        Matcher m = REMOVE_SELECT_PATTERN.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            int c = m.end() - m.start();
            m.appendReplacement(sb, getStr(c, "~"));
        }
        m.appendTail(sb);

        String replacedSql = sb.toString();

        int index = replacedSql.indexOf("from");

        // 如果不存在
        if (index == -1) {
            index = replacedSql.indexOf("FROM");
        }
        return sql.substring(index);
    }

    /**
     * 根据一个Map的条件，生成where 语句
     * @param where  key为条件，value为条件值
     * @return where 语句
     */
    private String createWhereSql(Map<String,?> where){

        String whereSql = "";
        if (where != null) {
            Object[] whereCols = where.keySet().toArray();
            for (int i = 0; i < whereCols.length; i++) {
                StringBuffer str = new StringBuffer();
                str.append(whereCols[i].toString());
                str.append("=?");
                whereCols[i] = str.toString();
            }
            whereSql += StringUtil.implode(" AND ", whereCols);
        }

        return whereSql;
    }
}
