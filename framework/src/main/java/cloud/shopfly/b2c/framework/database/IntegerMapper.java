/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 整型mapper
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class IntegerMapper implements RowMapper<Integer> {
	
	@Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer  v = rs.getInt(1);
		return v;
	}

}
