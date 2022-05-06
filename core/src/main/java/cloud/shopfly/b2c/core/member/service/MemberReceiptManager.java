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
 * 会员发票业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:13
 */
public interface MemberReceiptManager {

    /**
     * 根据发票类型查询当前会员发票列表
     *
     * @param receiptType 发票类型
     * @return
     */
    List<MemberReceipt> list(String receiptType);


    /**
     * 添加会员发票
     *
     * @param memberReceiptVO 会员发票
     * @return MemberReceipt 会员发票
     */
    MemberReceipt add(MemberReceiptVO memberReceiptVO);

    /**
     * 修改会员发票
     *
     * @param memberReceiptVO 会员发票
     * @param id              会员发票主键
     * @return MemberReceipt 会员发票
     */
    MemberReceipt edit(MemberReceiptVO memberReceiptVO, Integer id);

    /**
     * 删除会员发票
     *
     * @param id 会员发票主键
     */
    void delete(Integer id);

    /**
     * 获取会员发票
     *
     * @param id 会员发票主键
     * @return MemberReceipt  会员发票
     */
    MemberReceipt getModel(Integer id);

    /**
     * 设置默认发票，如果发票是个人则发票id为0
     *
     * @param receiptType 发票类型
     * @param id          发票id
     */
    void setDefaultReceipt(String receiptType, Integer id);

}