/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.service;

import com.enation.app.javashop.core.system.model.dos.ShipTemplateDO;
import com.enation.app.javashop.core.system.model.vo.ShipTemplateSellerVO;
import com.enation.app.javashop.core.system.model.vo.ShipTemplateVO;

import java.util.List;

/**
 * 运费模版业务层
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
public interface ShipTemplateManager {


	/**
	 * 新增
	 * @param tamplate
	 * @return
	 */
	ShipTemplateDO save(ShipTemplateSellerVO tamplate);

	/**
	 * 修改
	 * @param template
	 * @return
	 */
	ShipTemplateDO edit(ShipTemplateSellerVO template);


	/**
	 * 获取商家运送方式
	 * @return
	 */
	List<ShipTemplateSellerVO> getStoreTemplate();


	/**
	 * 获取商家运送方式
	 * @param templateId
	 * @return
	 */
	ShipTemplateVO getFromCache(Integer templateId);

	/**
	 * 删除
	 * @param templateId
	 */
	void delete(Integer templateId);

	/**
	 * 数据库中查询一个运费模板VO
	 * @param templateId
	 * @return
	 */
	ShipTemplateSellerVO getFromDB(Integer templateId);
}