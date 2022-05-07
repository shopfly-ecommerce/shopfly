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
 * Transaction status
 *
 * @author Snow
 * @version v1.0
 * @date 2017years8month18On the afternoon9:20:46
 * @since v6.4.0
 */
public enum TradeStatusEnum {

    /**
     * The new orders
     */
    NEW("The new orders"),

    /**
     * Payment has been
     */
    PAID_OFF("Payment has been");

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
