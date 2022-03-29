/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.database.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



/**
 * 覆写jdbctemlate ，使用LowerCaseColumnMapRowMapper
 * @author kingapex
 * 2010-6-13上午11:05:32
 */
public class LowerCaseJdbcTemplate extends JdbcTemplate {
	@Override
	protected RowMapper getColumnMapRowMapper() {

		return new MySqlColumnMapRowMapper();

	}

}
