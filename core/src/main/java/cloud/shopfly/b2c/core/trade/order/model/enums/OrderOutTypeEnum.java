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
package cloud.shopfly.b2c.core.trade.order.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单出库的类型
 *
 * @author Snow create in 2018/7/10
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderOutTypeEnum {

    /**
     * 商品（除以下两种之外的所有商品）
     */
    GOODS("商品"),

    /**
     * 团购商品
     */
    GROUPBUY_GOODS("团购商品"),

    /**
     * 限时抢购商品
     */
    SECKILL_GOODS("限时抢购商品");


    private String description;

    OrderOutTypeEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }


    public static List<String> getAll() {
        List<String> all = new ArrayList<>();
        all.add(OrderOutTypeEnum.GOODS.name());
        all.add(OrderOutTypeEnum.GROUPBUY_GOODS.name());
        all.add(OrderOutTypeEnum.SECKILL_GOODS.name());
        return all;
    }
}
