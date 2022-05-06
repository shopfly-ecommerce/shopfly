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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateDO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSellerVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;

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