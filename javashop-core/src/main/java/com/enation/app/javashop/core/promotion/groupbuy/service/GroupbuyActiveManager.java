/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.groupbuy.service;

import com.enation.app.javashop.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 团购活动表业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:52:14
 */
public interface GroupbuyActiveManager	{

	/**
	 * 查询团购活动表列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加团购活动表
	 * @param groupbuyActive 团购活动表
	 * @return GroupbuyActive 团购活动表
	 */
	GroupbuyActiveDO add(GroupbuyActiveDO groupbuyActive);

	/**
	* 修改团购活动表
	* @param groupbuyActive 团购活动表
	* @param id 团购活动表主键
	* @return GroupbuyActive 团购活动表
	*/
	GroupbuyActiveDO edit(GroupbuyActiveDO groupbuyActive, Integer id);

	/**
	 * 删除团购活动表
	 * @param id 团购活动表主键
	 */
	void delete(Integer id);

	/**
	 * 获取团购活动表
	 * @param id 团购活动表主键
	 * @return GroupbuyActive  团购活动表
	 */
	GroupbuyActiveDO getModel(Integer id);


	/**
	 * 读取正在进行的活动列表
	 * @return
	 */
	List<GroupbuyActiveDO> getActiveList();


	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param id
	 */
	void verifyAuth(Integer id);

}
