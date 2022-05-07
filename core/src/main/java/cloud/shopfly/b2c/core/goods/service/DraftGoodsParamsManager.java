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
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;

import java.util.List;

/**
 * Draft commodity parameter sheet business layer
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:31:20
 */
public interface DraftGoodsParamsManager {

	/**
	 * Adds a set of parameters for the draft item
	 * @param goodsParamsList
	 * @param draftGoodsId
	 */
	void addParams(List<GoodsParamsDO> goodsParamsList, Integer draftGoodsId);

	/**
	 * Query the parameters associated with a category and return the added values
	 * @param categoryId
	 * @param draftGoodsId
	 * @return
	 */
	List<GoodsParamsGroupVO> getParamByCatAndDraft(Integer categoryId, Integer draftGoodsId);

}
