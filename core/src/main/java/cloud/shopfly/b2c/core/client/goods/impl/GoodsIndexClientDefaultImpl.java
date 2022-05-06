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

import cloud.shopfly.b2c.core.client.goods.GoodsIndexClient;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品索引
 * @date 2018/8/14 14:13
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class GoodsIndexClientDefaultImpl implements GoodsIndexClient {

    @Autowired
    private GoodsIndexManager goodsIndexManager;

    @Override
    public void addIndex(Map goods) {
        goodsIndexManager.addIndex(goods);
    }

    @Override
    public void updateIndex(Map goods) {
        goodsIndexManager.updateIndex(goods);

    }

    @Override
    public void deleteIndex(Map goods) {
        goodsIndexManager.deleteIndex(goods);
    }

    @Override
    public boolean addAll(List<Map<String, Object>> list, int index) {
        return  goodsIndexManager.addAll(list,index);
    }
}
