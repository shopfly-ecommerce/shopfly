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
package cloud.shopfly.b2c.core.payment.service;

import java.util.Map;

/**
 * 退款接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-15
 */

public interface RefundManager {

    /**
     * 原路退回
     *
     * @param returnTradeNo 第三方订单号
     * @param refundSn      退款编号
     * @param refundPrice   退款金额
     * @return
     */
    Map originRefund(String returnTradeNo, String refundSn, Double refundPrice);

    /**
     * 查询退款状态
     *
     * @param returnTradeNo 第三方订单号
     * @param refundSn      退款编号
     * @return
     */
    String queryRefundStatus(String returnTradeNo, String refundSn);


}
