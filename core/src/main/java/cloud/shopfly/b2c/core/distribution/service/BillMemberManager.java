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
 * 用户结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午8:51
 */

public interface BillMemberManager {

    /**
     * 获取某个会员的下线业绩
     *
     * @param memberId
     * @param billId
     * @return
     */
    List<BillMemberVO> allDown(Integer memberId, Integer billId);


    /**
     * 获取结算单
     *
     * @param totalSn
     * @param memberId
     * @return
     */
    BillMemberDO getBillByTotalSn(String totalSn, Integer memberId);

    /**
     * 分页获取会员历史业绩
     *
     * @param memberId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BillMemberVO> getAllByMemberId(Integer memberId, Integer pageNo, Integer pageSize);

    /**
     * 新增一个结算单
     *
     * @param billMember
     * @return
     */
    BillMemberDO add(BillMemberDO billMember);

    /**
     * 查询一个总结算单的会员结算单
     *
     * @param page
     * @param pageSize
     * @param id
     * @param uname
     * @return page
     */
    Page<BillMemberVO> page(Integer page, Integer pageSize, Integer id, String uname);


    /**
     * 获取分销商结算单
     *
     * @param billId 总结算单id
     * @return do
     */
    BillMemberDO getBillMember(Integer billId);


    /**
     * 购买商品产生的结算
     *
     * @param order
     */
    void buyShop(DistributionOrderDO order);

    /**
     * 退货商品产生的结算
     *
     * @param order
     * @param distributionRefundDTO
     */
    void returnShop(DistributionOrderDO order, DistributionRefundDTO distributionRefundDTO);

    /**
     * 获取分销账单
     *
     * @param page
     * @param pageSize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionOrderVO> listOrder(Integer page, Integer pageSize, Integer id, Integer memberId);


    /**
     * 获取分销退款单
     *
     * @param page
     * @param pagesize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionSellbackOrderVO> listSellback(Integer page, Integer pagesize, Integer id, Integer memberId);

    /**
     * 获取当前分销商当月结算单
     * @param memberId
     * @param billId
     * @return
     */
    BillMemberVO getCurrentBillMember(Integer memberId, Integer billId);

    /**
     * 获取指定sn的分销商结算单
     *
     * @param memberId
     * @param sn
     * @return do
     */
    BillMemberDO getHistoryBillMember(Integer memberId, String sn);


    /**
     * 结算单会员分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param memberId
     * @return page
     */
    Page<BillMemberVO> billMemberPage(Integer pageNo, Integer pageSize, Integer memberId);

}
