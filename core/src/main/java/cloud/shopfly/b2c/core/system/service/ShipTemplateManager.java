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
 * Freight template business layer
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
public interface ShipTemplateManager {


	/**
	 * new
	 * @param tamplate
	 * @return
	 */
	ShipTemplateDO save(ShipTemplateSellerVO tamplate);

	/**
	 * edit
	 * @param template
	 * @return
	 */
	ShipTemplateDO edit(ShipTemplateSellerVO template);


	/**
	 * Get merchant shipping method
	 * @return
	 */
	List<ShipTemplateSellerVO> getStoreTemplate();


	/**
	 * Get merchant shipping method
	 * @param templateId
	 * @return
	 */
	ShipTemplateVO getFromCache(Integer templateId);

	/**
	 * delete
	 * @param templateId
	 */
	void delete(Integer templateId);

	/**
	 * Query a freight template in the databaseVO
	 * @param templateId
	 * @return
	 */
	ShipTemplateSellerVO getFromDB(Integer templateId);

	/**
	 * According to the areaidQuery the number of areas used
	 * @param rateAreaId
	 * @return
	 */
    Integer getCountByAreaId(Integer rateAreaId);

	/**
	 * According to the areaidRemove shipping template
	 * @param rateAreaId
	 */
	void removeCache(Integer rateAreaId);
}
