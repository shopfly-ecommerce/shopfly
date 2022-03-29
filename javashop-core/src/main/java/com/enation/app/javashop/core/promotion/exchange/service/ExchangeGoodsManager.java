/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.exchange.service;

import com.enation.app.javashop.core.promotion.exchange.model.dos.ExchangeDO;
import com.enation.app.javashop.core.promotion.exchange.model.dto.ExchangeQueryParam;
import com.enation.app.javashop.core.promotion.tool.model.dto.PromotionGoodsDTO;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 积分商品业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:47:18
 */
public interface ExchangeGoodsManager {


	/**
	 * 查询积分商品列表
	 * @param param
	 * @return
	 */
	Page list(ExchangeQueryParam param);

	/**
	 * 添加积分兑换
	 * @param exchangeSetting 积分兑换
	 * @param goodsDTO 商品DTO
	 * @return ExchangeSetting 积分兑换 */
	ExchangeDO add(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO);

	/**
	* 修改积分兑换
	* @param exchangeSetting 积分兑换
	* @param goodsDTO 商品DTO
	* @return ExchangeSetting 积分兑换
	*/
	ExchangeDO edit(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO);

	/**
	 * 删除积分兑换
	 * @param id 积分兑换主键
	 */
	void delete(Integer id);

	/**
	 * 获取积分兑换
	 * @param id 积分兑换主键
	 * @return ExchangeSetting  积分兑换
	 */
	ExchangeDO getModel(Integer id);

	/**
	 * 查询某个商品的积分兑换信息
	 * @param goodsId
	 * @return
	 */
    ExchangeDO getModelByGoods(Integer goodsId);

	/**
	 * 删除某个商品的积分信息
	 * @param goodsId
	 */
	void deleteByGoods(Integer goodsId);

	/**
	 * 查询某个积分分类的积分兑换信息
	 * @param categoryId
	 * @return
	 */
	List<ExchangeDO> getModelByCategoryId(Integer categoryId);
}
