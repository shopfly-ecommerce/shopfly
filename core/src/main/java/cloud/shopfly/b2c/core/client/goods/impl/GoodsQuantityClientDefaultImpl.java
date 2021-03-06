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
package cloud.shopfly.b2c.core.client.goods.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsQuantityClient;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Inventory operation realization
 *
 * @author zh
 * @version v7.0
 * @date 18/9/20 In the afternoon7:33
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class GoodsQuantityClientDefaultImpl implements GoodsQuantityClient {

    @Autowired
    private GoodsQuantityManager goodsQuantityManager;


    @Override
    public boolean updateSkuQuantity( List<GoodsQuantityVO> goodsQuantityList) {

        return goodsQuantityManager.updateSkuQuantity(goodsQuantityList);
    }

}
