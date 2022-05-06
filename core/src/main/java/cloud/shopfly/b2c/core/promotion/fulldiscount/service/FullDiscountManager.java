/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.promotion.fulldiscount.service;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.framework.database.Page;

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
