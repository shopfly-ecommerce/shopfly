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

import cloud.shopfly.b2c.core.member.model.dos.MemberReceipt;
import cloud.shopfly.b2c.core.member.model.vo.MemberReceiptVO;

import java.util.List;

/**
 * Member invoice business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:13
 */
public interface MemberReceiptManager {

    /**
     * Query current member invoice list by invoice type
     *
     * @param receiptType Invoice type
     * @return
     */
    List<MemberReceipt> list(String receiptType);


    /**
     * Add membership invoice
     *
     * @param memberReceiptVO Member of the invoice
     * @return MemberReceipt Member of the invoice
     */
    MemberReceipt add(MemberReceiptVO memberReceiptVO);

    /**
     * Revision of member invoice
     *
     * @param memberReceiptVO Member of the invoice
     * @param id              Member invoice master key
     * @return MemberReceipt Member of the invoice
     */
    MemberReceipt edit(MemberReceiptVO memberReceiptVO, Integer id);

    /**
     * Delete member invoice
     *
     * @param id Member invoice master key
     */
    void delete(Integer id);

    /**
     * Get membership invoice
     *
     * @param id Member invoice master key
     * @return MemberReceipt  Member of the invoice
     */
    MemberReceipt getModel(Integer id);

    /**
     * Set the default invoice, or if the invoice is a personidfor0
     *
     * @param receiptType Invoice type
     * @param id          invoiceid
     */
    void setDefaultReceipt(String receiptType, Integer id);

}
