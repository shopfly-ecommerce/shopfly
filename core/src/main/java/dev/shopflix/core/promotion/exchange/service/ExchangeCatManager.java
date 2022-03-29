/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.exchange.service;

import dev.shopflix.core.promotion.exchange.model.dos.ExchangeCat;

import java.util.List;

/**
 * 积分兑换分类业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-29 16:56:22
 */
public interface ExchangeCatManager	{

	/**
	 * 查询积分兑换分类列表
	 * @param parentId 父ID
	 * @return Page
	 */
	List<ExchangeCat> list(Integer parentId);

	/**
	 * 添加积分兑换分类
	 * @param exchangeCat 积分兑换分类
	 * @return ExchangeCat 积分兑换分类
	 */
	ExchangeCat add(ExchangeCat exchangeCat);

	/**
	* 修改积分兑换分类
	* @param exchangeCat 积分兑换分类
	* @param id 积分兑换分类主键
	* @return ExchangeCat 积分兑换分类
	*/
	ExchangeCat edit(ExchangeCat exchangeCat, Integer id);

	/**
	 * 删除积分兑换分类
	 * @param id 积分兑换分类主键
	 */
	void delete(Integer id);

	/**
	 * 获取积分兑换分类
	 * @param id 积分兑换分类主键
	 * @return ExchangeCat  积分兑换分类
	 */
	ExchangeCat getModel(Integer id);

}
