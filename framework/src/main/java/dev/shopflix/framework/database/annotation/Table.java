/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识为数据库的表名
 * @author Snow
 */
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface Table {

	/**
	 * 数据库的表名
	 * @return
	 */
	String name();
}
