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
package cloud.shopfly.b2c.core.client.goods;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;

import java.util.List;

/**
 * 商品库存操作客户端
 * @author zh
 * @version v1.0
 * @date 18/9/20 下午7:31
 * @since v7.0
 *
 * @version 2.0
 * 统一为一个接口（更新接口）<br/>
 * 内部实现为redis +lua 保证原子性 -- by kingapex 2019-01-17
 */
public interface GoodsQuantityClient {


    /**
     * 库存更新接口
     * @param goodsQuantityList 要扣减的库存vo List
     * @return 如果扣减成功返回真，否则返回假
     */
    boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList);
}


