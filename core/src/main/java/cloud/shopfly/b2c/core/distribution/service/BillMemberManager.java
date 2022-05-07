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

import cloud.shopfly.b2c.core.distribution.model.dos.BillMemberDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import cloud.shopfly.b2c.core.distribution.model.dto.DistributionRefundDTO;
import cloud.shopfly.b2c.core.distribution.model.vo.BillMemberVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Customer statement
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the morning8:51
 */

public interface BillMemberManager {

    /**
     * Get a members referral performance
     *
     * @param memberId
     * @param billId
     * @return
     */
    List<BillMemberVO> allDown(Integer memberId, Integer billId);


    /**
     * Get the statement
     *
     * @param totalSn
     * @param memberId
     * @return
     */
    BillMemberDO getBillByTotalSn(String totalSn, Integer memberId);

    /**
     * Page access to member history performance
     *
     * @param memberId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BillMemberVO> getAllByMemberId(Integer memberId, Integer pageNo, Integer pageSize);

    /**
     * Add a new statement
     *
     * @param billMember
     * @return
     */
    BillMemberDO add(BillMemberDO billMember);

    /**
     * Query a members statement of a master statement
     *
     * @param page
     * @param pageSize
     * @param id
     * @param uname
     * @return page
     */
    Page<BillMemberVO> page(Integer page, Integer pageSize, Integer id, String uname);


    /**
     * Obtain distributor statements
     *
     * @param billId General statementid
     * @return do
     */
    BillMemberDO getBillMember(Integer billId);


    /**
     * The settlement of goods purchased
     *
     * @param order
     */
    void buyShop(DistributionOrderDO order);

    /**
     * Settlement of returned goods
     *
     * @param order
     * @param distributionRefundDTO
     */
    void returnShop(DistributionOrderDO order, DistributionRefundDTO distributionRefundDTO);

    /**
     * Get distribution bill
     *
     * @param page
     * @param pageSize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionOrderVO> listOrder(Integer page, Integer pageSize, Integer id, Integer memberId);


    /**
     * Get distribution refund slip
     *
     * @param page
     * @param pagesize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionSellbackOrderVO> listSellback(Integer page, Integer pagesize, Integer id, Integer memberId);

    /**
     * Obtain the current distributors monthly statement
     * @param memberId
     * @param billId
     * @return
     */
    BillMemberVO getCurrentBillMember(Integer memberId, Integer billId);

    /**
     * Access to specifysnDistributor statement
     *
     * @param memberId
     * @param sn
     * @return do
     */
    BillMemberDO getHistoryBillMember(Integer memberId, String sn);


    /**
     * Statement member paging query
     *
     * @param pageNo
     * @param pageSize
     * @param memberId
     * @return page
     */
    Page<BillMemberVO> billMemberPage(Integer pageNo, Integer pageSize, Integer memberId);

}
