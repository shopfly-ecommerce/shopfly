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
package cloud.shopfly.b2c.core.client.member;

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;

/**
 * 会员发票历史查询客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午2:55
 * @since v7.0
 */

public interface MemberHistoryReceiptClient {

    /**
     * 根据订单sn查询历史发票信息
     *
     * @param orderSn 订单sn
     * @return 历史发票信息
     */
    ReceiptHistory getReceiptHistory(String orderSn);

    /**
     * 添加发票历史
     *
     * @param receiptHistory 发票历史
     * @return ReceiptHistory 发票历史
     */
    ReceiptHistory add(ReceiptHistory receiptHistory);

}
