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
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.distribution.model.dos.WithdrawApplyDO;
import cloud.shopfly.b2c.core.distribution.model.vo.BankParamsVO;
import cloud.shopfly.b2c.core.distribution.model.vo.WithdrawApplyVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.Map;


/**
 * Withdrawal interface
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon1:18
 */
public interface WithdrawManager {

    /**
     * According to theIDDetailed record of withdrawal application
     *
     * @param id
     * @return
     */
    WithdrawApplyDO getModel(Integer id);

    /**
     * To apply for cash withdrawals
     *
     * @param memberId    membersid
     * @param applyMoney  To apply for the amount
     * @param applyRemark note
     */
    void applyWithdraw(Integer memberId, Double applyMoney, String applyRemark);


    /**
     * Review withdrawal request
     *
     * @param applyId     Withdrawal applicationid
     * @param remark      note
     * @param auditResult Review the results
     */
    void auditing(Integer applyId, String remark, String auditResult);


    /**
     * Financial money
     *
     * @param applyId Withdrawal applicationid
     * @param remark  note
     */
    void transfer(Integer applyId, String remark);


    /**
     * According to themember_idQuery withdrawal records
     *
     * @param memeberId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<WithdrawApplyVO> pageWithdrawApply(Integer memeberId, Integer pageNo, Integer pageSize);

    /**
     * Save the withdrawal Settings
     *
     * @param bankParams
     */
    void saveWithdrawWay(BankParamsVO bankParams);

    /**
     * Gets the withdrawal Settings
     *
     * @param memberId
     * @return
     */
    BankParamsVO getWithdrawSetting(int memberId);

    /**
     * Paging member withdrawal query
     *
     * @param pageNo
     * @param pageSize
     * @param map
     * @return
     */
    Page<WithdrawApplyVO> pageApply(Integer pageNo, Integer pageSize, Map<String, String> map);


    /**
     * Paging member withdrawal query
     *
     * @param memberId
     * @return
     */
    Double getRebate(Integer memberId);
}
