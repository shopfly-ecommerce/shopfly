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

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * distributorsManagerinterface
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the afternoon3:19
 */
public interface DistributionManager {

    /**
     * New distributor
     *
     * @param distributor
     * @return
     */
    DistributionDO add(DistributionDO distributor);

    /**
     * All referrals
     *
     * @param memberId
     * @return
     */
    List<DistributionVO> allDown(Integer memberId);


    /**
     * Paging distributor
     *
     * @param pageNo     The page number
     * @param pageSize   Page size
     * @param memberName The member name
     * @return PAGE
     */
    Page page(Integer pageNo, Integer pageSize, String memberName);

    /**
     * According to the membershipidGet distributor information
     *
     * @param memberId membersid
     * @return Distributor objectDistributor,Return withoutnull
     */
    DistributionDO getDistributorByMemberId(Integer memberId);

    /**
     * According to the membershipidGet distributor information
     *
     * @param id distributorsid
     * @return Distributor objectDistributor,Return withoutnull
     */
    DistributionDO getDistributor(Integer id);


    /**
     * updateDistributorinformation
     *
     * @param distributor
     * @return
     */
    DistributionDO edit(DistributionDO distributor);


    /**
     * According to the membershipidSet up its superior distributor（Two levels of）
     *
     * @param memberId membersid
     * @param parentId Superior membersid
     * @return Set the result, trun=successfulfalse=failure
     */
    boolean setParentDistributorId(Integer memberId, Integer parentId);

    /**
     * Get the amount available for withdrawal
     *
     * @param memberId
     * @return
     */
    Double getCanRebate(Integer memberId);

    /**
     * Increase the amount of frozen rebate
     *
     * @param price    Rebate amount amount
     * @param memberId membersid
     */
    void addFrozenCommission(Double price, Integer memberId);


    /**
     * Increase total sales、Total rebate amount amount
     *
     * @param orderPrice Amount
     * @param rebate     The rebate amount
     * @param memberId   membersid
     */
    void addTotalPrice(Double orderPrice, Double rebate, Integer memberId);

    /**
     * Minus total sales、Total rebate amount amount
     *
     * @param orderPrice Amount
     * @param rebate     The rebate amount
     * @param memberId   membersid
     */
    void subTotalPrice(Double orderPrice, Double rebate, Integer memberId);

    /**
     * Gets the current members superior
     *
     * @return String returned
     */
    String getUpMember();

    /**
     * Get a collection of sub-distributors
     *
     * @param memberId
     * @return
     */
    List<DistributionVO> getLowerDistributorTree(Integer memberId);

    /**
     * Modify the template
     *
     * @param memberId
     * @param tplId
     */
    void changeTpl(Integer memberId, Integer tplId);

    /**
     * Collecting the Number of Offline Users
     *
     * @param memberId
     */
    void countDown(Integer memberId);

}
