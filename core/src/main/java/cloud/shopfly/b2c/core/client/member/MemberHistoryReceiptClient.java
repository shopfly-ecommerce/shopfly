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
 * Member invoice history query client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 In the afternoon2:55
 * @since v7.0
 */

public interface MemberHistoryReceiptClient {

    /**
     * According to the ordersnExample Query historical invoice information
     *
     * @param orderSn The ordersn
     * @return Historical invoice Information
     */
    ReceiptHistory getReceiptHistory(String orderSn);

    /**
     * Add invoice history
     *
     * @param receiptHistory Invoice history
     * @return ReceiptHistory Invoice history
     */
    ReceiptHistory add(ReceiptHistory receiptHistory);

}
