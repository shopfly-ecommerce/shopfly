/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.util;

/**
 * 测试通用类
 * 提供测试开关 统一打印方法
 * @author Sylow
 * @version v1.0 , 2015-08-24
 * @since v4.0
 * 
 */
public class TestUtil {
	
	/**
	 * 打印开关
	 */
	public final static boolean TEST_OPEN = false;
	
	
	/**
	 * 打印错误信息
	 * @param e <b>Exception</b>
	 */
	public static void print(Exception e){
		if (TEST_OPEN) {
			e.printStackTrace();
		}
	}

}
