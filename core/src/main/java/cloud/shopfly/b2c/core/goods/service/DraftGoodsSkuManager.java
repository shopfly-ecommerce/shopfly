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

import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;

import java.util.List;

/**
 * 草稿商品sku业务层
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:38:06
 */
public interface DraftGoodsSkuManager {

	/**
	 * 添加sku规格列表
	 * @param goodsVO
	 * @param draftGoodsId
	 */
	void add(GoodsDTO goodsVO, Integer draftGoodsId);

	/**
	 * 查询草稿箱的sku列表
	 * @param draftGoodsId
	 * @return
	 */
	List<GoodsSkuVO> getSkuList(Integer draftGoodsId);

}