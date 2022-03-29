/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.fulldiscount.service;

import dev.shopflix.core.goods.model.enums.QuantityType;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 满优惠赠品业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
public interface FullDiscountGiftManager	{

	/**
	 * 查询满优惠赠品列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keyword 关键字
	 * @return Page
	 */
	Page list(int page, int pageSize, String keyword);

	/**
	 * 添加满优惠赠品
	 * @param fullDiscountGift 满优惠赠品
	 * @return FullDiscountGift 满优惠赠品
	 */
	FullDiscountGiftDO add(FullDiscountGiftDO fullDiscountGift);

	/**
	 * 修改满优惠赠品
	 * @param fullDiscountGift 满优惠赠品
	 * @param id 满优惠赠品主键
	 * @return FullDiscountGift 满优惠赠品
	 */
	FullDiscountGiftDO edit(FullDiscountGiftDO fullDiscountGift, Integer id);

	/**
	 * 删除满优惠赠品
	 * @param id 满优惠赠品主键
	 */
	void delete(Integer id);

	/**
	 * 获取满优惠赠品
	 * @param id 满优惠赠品主键
	 * @return FullDiscountGift  满优惠赠品
	 */
	FullDiscountGiftDO getModel(Integer id);


	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param id
	 */
	void verifyAuth(Integer id);

	/**
	 * 增加赠品库存
	 * @param giftList
	 * @return
	 */
	boolean addGiftQuantity(List<FullDiscountGiftDO> giftList);

	/**
	 * 增加赠品可用库存
	 * @param giftList
	 * @return
	 */
	boolean addGiftEnableQuantity(List<FullDiscountGiftDO> giftList);


	/**
	 * 减少赠品库存
	 * @param giftList
	 * @param type
	 * @return
	 */
	boolean reduceGiftQuantity(List<FullDiscountGiftDO> giftList, QuantityType type);

	/**
	 * 获取商家所有赠品数据集合
	 * @return
	 */
	List<FullDiscountGiftDO> listAll();
}
