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
package cloud.shopfly.b2c.core.payment.model.enums;

/**
 * 交易类型
 *
 * @author kingapex
 * @version 1.0
 * @since pangu1.0
 * 2017年4月5日下午5:12:55
 */
public enum TradeType {

    /**
     * 订单类型
     */
    order("订单"),

    /**
     * 交易类型
     */
    trade("交易"),

    /**
     * 调试器类型（程序调试用，不会人为用到）
     */
    debugger("调试器");

    private String description;

    TradeType(String description) {
        this.description = description;
    }
}
