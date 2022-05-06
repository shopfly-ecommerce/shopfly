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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 发票历史业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
public interface ReceiptHistoryManager {

    /**
     * 查询发票历史列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加发票历史
     *
     * @param receiptHistory 发票历史
     * @return ReceiptHistory 发票历史
     */
    ReceiptHistory add(ReceiptHistory receiptHistory);

    /**
     * 根据订单sn查询历史发票信息
     *
     * @param orderSn 订单sn
     * @return 历史发票信息
     */
    ReceiptHistory getReceiptHistory(String orderSn);


    /**
     * 获取发票历史详细信息，包括收货地址已经商品sku相关信息
     *
     * @param historyId 发票历史的id
     * @return 发票详细VO
     */
    ReceiptHistoryVO getReceiptDetail(Integer historyId);

}