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

/**
 * 交易状态
 *
 * @author Snow
 * @version v1.0
 * @date 2017年8月18日下午9:20:46
 * @since v6.4.0
 */
public enum TradeStatusEnum {

    /**
     * 新订单
     */
    NEW("新订单"),

    /**
     * 已付款
     */
    PAID_OFF("已付款");

    private String description;

    TradeStatusEnum(String description) {
        this.description = description;

    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
