/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.groupbuy.service;

import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;

import java.util.List;

/**
 * 团购商品库存日志表业务层
 * @author xlp
 * @version v1.0
 * @since v7.0.0
 * 2018-07-09 15:32:29
 */
public interface GroupbuyQuantityLogManager {

	/**
	 * 还原团购库存
	 * @param orderSn 订单编号
	 * @return 团购取消订单日志
	 */
	List<GroupbuyQuantityLog> rollbackReduce(String orderSn);

	/**
	 * 添加团购商品库存日志表
	 * @param groupbuyQuantityLog 团购商品库存日志表
	 * @return 团购商品库存日志表
	 */
	GroupbuyQuantityLog add(GroupbuyQuantityLog groupbuyQuantityLog);



}
