/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库操作运行期异常
 * @author kingapex
 * 2010-1-6下午06:16:47
 */
public class DBRuntimeException extends RuntimeException {

 
	private static final long serialVersionUID = -368646349014580765L;
	
	private static final Log loger = LogFactory
			.getLog(DBRuntimeException.class);

	
	public DBRuntimeException(String message){
		super(message);
	}
	public DBRuntimeException(Exception e,String sql) {
		
		super("数据库运行期异常");
		e.printStackTrace();
		if(loger.isErrorEnabled()){
			loger.error("数据库运行期异常，相关sql语句为"+ sql);
		}
	}
	
	
}
