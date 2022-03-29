/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagecreate.service;
/**
 * 
 * 静态页生成接口
 * @author zh
 * @version v1.0
 * @since v6.4.0
 * 2017年9月1日 上午11:47:00
 */
public interface PageCreateManager {
	/**
	 * 开始生成静态页
	 * @param choosePages
	 * @return
	 */
	boolean startCreate(String[] choosePages) ;
}
