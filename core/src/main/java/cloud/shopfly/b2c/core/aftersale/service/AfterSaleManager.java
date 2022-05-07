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
package cloud.shopfly.b2c.core.aftersale.service;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDTO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDetailDTO;
import cloud.shopfly.b2c.core.aftersale.model.vo.*;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.aftersale.model.vo.*;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * After-sales management interface
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the afternoon3:07 2018/5/2
 */
public interface AfterSaleManager {

    /**
     * To apply for a refund
     *
     * @param refundApply Refund application
     */
    void applyRefund(BuyerRefundApplyVO refundApply);


    /**
     * Return to apply for
     *
     * @param refundApply
     */
    void applyGoodsReturn(BuyerRefundApplyVO refundApply);

    /**
     * Buyer cancels paid orders
     *
     * @param buyerCancelOrderVO
     */
    void cancelOrder(BuyerCancelOrderVO buyerCancelOrderVO);

    /**
     * The administrator approves a return（paragraph）
     *
     * @param refundApproval approvalvo
     * @param permission permissions
     * @return approvalvo
     */
    AdminRefundApprovalVO approval(AdminRefundApprovalVO refundApproval, Permission permission);

    /**
     * Financial audit/Execute a refund
     * @param refundApproval
     * @return
     */
    FinanceRefundApprovalVO approval(FinanceRefundApprovalVO refundApproval);

    /**
     * Query a refund based on the parameters（cargo）single
     *
     * @param param Query parameters
     * @return
     */
    Page<RefundDTO> query(RefundQueryParamVO param);

    /**
     * Get details by number
     *
     * @param sn Receipt number
     * @return
     */
    RefundDetailDTO getDetail(String sn);

    /**
     * Query that the refund mode is the original route return and the status is refund
     *
     * @return
     */
    List<RefundDO> queryNoReturnOrder();

    /**
     * Update the status of refund receipt
     *
     * @param list A single list of refunds
     */
    void update(List<RefundDO> list);

    /**
     * Obtain refund application information
     *
     * @param orderSn
     * @param skuId
     * @return
     */
    RefundApplyVO refundApply(String orderSn, Integer skuId);

    /**
     * Obtain the number of outstanding orders
     *
     * @param memberId
     * @return
     */
    Integer getAfterSaleCount(Integer memberId);


    /**
     * Get a list of items for the return
     *
     * @param sn The refund number
     * @return List of Returned Goods
     */
    List<RefundGoodsDO> getRefundGoods(String sn);

    /**
     * Query the status of the refund slip
     */
    void queryRefundStatus();

    /**
     * Put in storage
     *
     * @param sn     Refund single
     * @param remark note
     */
    void stockIn(String sn, String remark);

    /**
     * A refund
     *
     * @param sn     The refund number
     * @param remark The refund note
     */
    void refund(String sn, String remark);

    /**
     * The system cancels paid orders
     * @param buyerCancelOrderVO
     */
    void sysCancelOrder(BuyerCancelOrderVO buyerCancelOrderVO);

    /**
     * Refund Receipt Exportexcel
     * @param startTime The start time
     * @param endTime The end of time
     * @return Byte stream
     */
    List<ExportRefundExcelVO> exportExcel(long startTime, long endTime);

}
