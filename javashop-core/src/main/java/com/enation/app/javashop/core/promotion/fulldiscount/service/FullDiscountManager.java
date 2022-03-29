/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.fulldiscount.service;

import com.enation.app.javashop.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import com.enation.app.javashop.framework.database.Page;

/**
 * 满优惠活动业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:42:06
 */
public interface FullDiscountManager	{

	/**
	 * 查询满优惠活动列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keywords 关键字
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * 添加满优惠活动
	 * @param fullDiscount 满优惠活动
	 * @return FullDiscount 满优惠活动
	 */
	FullDiscountVO add(FullDiscountVO fullDiscount);

	/**
	* 修改满优惠活动
	* @param fullDiscount 满优惠活动
	* @param id 满优惠活动主键
	* @return FullDiscount 满优惠活动
	*/
	FullDiscountVO edit(FullDiscountVO fullDiscount, Integer id);

	/**
	 * 删除满优惠活动
	 * @param id 满优惠活动主键
	 */
	void delete(Integer id);

	/**
	 * 从数据库获取促销信息
	 * @param fdId 满优惠活动ID
	 * @return 满优惠活动实体
	 */
	FullDiscountVO getModel(Integer fdId);


	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param id
	 */
	void verifyAuth(Integer id);

}
