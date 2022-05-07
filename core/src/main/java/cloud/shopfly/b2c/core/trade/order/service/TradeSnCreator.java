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
package cloud.shopfly.b2c.core.trade.order.service;

/**
 *  Transaction order number created
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeSnCreator {

    /**
     * Generate transaction number format as shown in：20171022000011
     * @return Transaction number
     */
    String generateTradeSn();


    /**
     * The generated order number format is as follows：20171022000011
     * @return Order no.
     */
    String generateOrderSn();

    /**
     * Generate the payment serial number format as follows：20171022000011
     * @return Order no.
     */
    String generatePayLogSn();

    /**
     * remove
     */
    void cleanCache();

}
