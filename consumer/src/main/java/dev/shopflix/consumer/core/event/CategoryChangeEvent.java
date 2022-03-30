/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;

import dev.shopflix.core.base.message.CategoryChangeMsg;

/**
 * 商品分类变化事件
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:24:31
 */
public interface CategoryChangeEvent {

	/**
	 * 商品分类变化后需要执行的方法
	 * @param categoryChangeMsg
	 */
    void categoryChange(CategoryChangeMsg categoryChangeMsg);
}
