/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.ShipTemplateDO;
import dev.shopflix.core.system.model.vo.ShipTemplateSellerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateVO;

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

	/**
	 * 根据区域id查询区域被使用数量
	 * @param rateAreaId
	 * @return
	 */
    Integer getCountByAreaId(Integer rateAreaId);

	/**
	 * 根据区域id移除运费模板
	 * @param rateAreaId
	 */
	void removeCache(Integer rateAreaId);
}