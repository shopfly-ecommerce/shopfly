/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;

/**
 * 商品索引生成事件
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:24:59
 */
public interface GoodsIndexInitEvent {

	/**
	 * 创建商品索引
	 */
    void createGoodsIndex();
}
