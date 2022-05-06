/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.database;

/**
 * 对像未找到异常<br>
 * 多用于根据某id查询一条记录，但此记录不存在
 * @author kingapex
 *2010-4-9上午09:09:20
 */
public class ObjectNotFoundException extends DBRuntimeException {

	public ObjectNotFoundException(String message) {
		super(message);
	}
		
	public ObjectNotFoundException(Exception e, String sql) {
		super(e, sql);
	 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403302876974180460L;

}
