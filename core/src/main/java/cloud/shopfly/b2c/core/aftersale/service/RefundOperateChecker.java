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

import cloud.shopfly.b2c.core.aftersale.model.vo.RefundStepVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundOperateEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 退货操作检测，看某状态下是否允许某操作
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午11:20 2018/5/2
 */
public class RefundOperateChecker {

    /**
     * 货到付款退款
     */
    private static final Map<RefundStatusEnum, RefundStepVO> COD_REFUND_FLOW = new HashMap<>();
    /**
     * 款到发货退款
     */
    private static final Map<RefundStatusEnum, RefundStepVO> ONLINE_REFUND_FLOW = new HashMap<>();
    /**
     * 货到付款退货
     */
    private static final Map<RefundStatusEnum, RefundStepVO> COD_RETURN_FLOW = new HashMap<>();
    /**
     * 款到发货退货
     */
    private static final Map<RefundStatusEnum, RefundStepVO> ONLINE_RETURN_FLOW = new HashMap<>();

    /**
     * 申请中状态下，买家可取消，卖家可审核
     */
    private static final RefundStepVO APPLY_STEP = new RefundStepVO(RefundStatusEnum.APPLY, RefundOperateEnum.CANCEL, RefundOperateEnum.ADMIN_APPROVAL);

    /**
     * 审核拒绝的状态下，可做的操作
     */
    private static final RefundStepVO REFUSE_STEP = new RefundStepVO(RefundStatusEnum.REFUSE);

    /**
     * 取消申请的状态下，可做的操作
     */
    private static final RefundStepVO CANCEL_STEP = new RefundStepVO(RefundStatusEnum.CANCEL);

    /**
     * 退款中状态下可做的操作
     */
    private static final RefundStepVO REFUNDING_STEP = new RefundStepVO(RefundStatusEnum.REFUNDING);

//	/**
//	 * 退款失败状态下，管理员可以审核
//	 */
//	private static final RefundStepVO REFUNDFAIL_STEP = new RefundStepVO(RefundStatusEnum.REFUNDFAIL,RefundOperateEnum.ADMIN_REFUND);

    /**
     * 已完成状态下，可做的操作
     */
    private static final RefundStepVO COMPLETED_STEP = new RefundStepVO(RefundStatusEnum.COMPLETED);

    static {
        initCodRefundflow();
        initOnlineRefundflow();
        initCodReturnflow();
        initOnlionReturnflow();
    }

    /**
     * 初始化货到付款退款流程
     */
    private static void initCodRefundflow() {


        //退款申请通过状态下，管理员可退款
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);


        // 退款失败状态下，管理员可退款
        RefundStepVO refundfailStep = new RefundStepVO(RefundStatusEnum.REFUNDFAIL, RefundOperateEnum.ADMIN_REFUND);

        COD_REFUND_FLOW.put(RefundStatusEnum.APPLY, APPLY_STEP);
        COD_REFUND_FLOW.put(RefundStatusEnum.CANCEL, CANCEL_STEP);
        COD_REFUND_FLOW.put(RefundStatusEnum.WAIT_FOR_MANUAL, waitForManualStep);
        COD_REFUND_FLOW.put(RefundStatusEnum.REFUSE, REFUSE_STEP);
        COD_REFUND_FLOW.put(RefundStatusEnum.REFUNDING, REFUNDING_STEP);
        COD_REFUND_FLOW.put(RefundStatusEnum.REFUNDFAIL, refundfailStep);
        COD_REFUND_FLOW.put(RefundStatusEnum.COMPLETED, COMPLETED_STEP);

    }

    /**
     * 初始化款到发货退款流程
     */
    private static void initOnlineRefundflow() {


        //退款申请通过状态下，管理员可退款
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        // 退款失败状态下，管理员可退款
        RefundStepVO refundfailStep = new RefundStepVO(RefundStatusEnum.REFUNDFAIL, RefundOperateEnum.ADMIN_REFUND);


        ONLINE_REFUND_FLOW.put(RefundStatusEnum.APPLY, APPLY_STEP);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.CANCEL, CANCEL_STEP);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.WAIT_FOR_MANUAL, waitForManualStep);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.REFUSE, REFUSE_STEP);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.REFUNDING, REFUNDING_STEP);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.REFUNDFAIL, refundfailStep);
        ONLINE_REFUND_FLOW.put(RefundStatusEnum.COMPLETED, COMPLETED_STEP);

    }

    /**
     * 初始化货到付款退货流程
     */
    private static void initCodReturnflow() {

        //退货申请通过状态下，管理员可退货入库
        RefundStepVO passStep = new RefundStepVO(RefundStatusEnum.PASS, RefundOperateEnum.STOCK_IN);

        //待人工处理和退款失败状态下，管理员可退款
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        //退款失败状态下，管理员可退款
        RefundStepVO refundfailStep = new RefundStepVO(RefundStatusEnum.REFUNDFAIL, RefundOperateEnum.ADMIN_REFUND);
        COD_RETURN_FLOW.put(RefundStatusEnum.APPLY, APPLY_STEP);
        COD_RETURN_FLOW.put(RefundStatusEnum.CANCEL, CANCEL_STEP);
        COD_RETURN_FLOW.put(RefundStatusEnum.PASS, passStep);
        COD_RETURN_FLOW.put(RefundStatusEnum.REFUSE, REFUSE_STEP);
        COD_RETURN_FLOW.put(RefundStatusEnum.WAIT_FOR_MANUAL, waitForManualStep);
        COD_RETURN_FLOW.put(RefundStatusEnum.REFUNDING, REFUNDING_STEP);
        COD_RETURN_FLOW.put(RefundStatusEnum.REFUNDFAIL, refundfailStep);
        COD_RETURN_FLOW.put(RefundStatusEnum.COMPLETED, COMPLETED_STEP);

    }

    /**
     * 初始化款到发货退货流程
     */
    private static void initOnlionReturnflow() {

        //退货申请通过状态下，卖家可退货入库
        RefundStepVO passStep = new RefundStepVO(RefundStatusEnum.PASS, RefundOperateEnum.STOCK_IN);

        //待人工处理状态下，管理员可退款
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        // 退款失败状态下，管理员可退款
        RefundStepVO refundfailStep = new RefundStepVO(RefundStatusEnum.REFUNDFAIL, RefundOperateEnum.ADMIN_REFUND);


        ONLINE_RETURN_FLOW.put(RefundStatusEnum.APPLY, APPLY_STEP);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.CANCEL, CANCEL_STEP);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.PASS, passStep);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.REFUSE, REFUSE_STEP);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.WAIT_FOR_MANUAL, waitForManualStep);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.REFUNDING, REFUNDING_STEP);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.REFUNDFAIL, refundfailStep);
        ONLINE_RETURN_FLOW.put(RefundStatusEnum.COMPLETED, COMPLETED_STEP);

    }

    /**
     * 校验操作是否允许
     *
     * @param type    退款类型
     * @param status  售后状态
     * @param operate 操作类型
     * @return 是否允许操作
     */
    public static boolean checkAllowable(RefuseTypeEnum type, PaymentTypeEnum paymentType, RefundStatusEnum status, RefundOperateEnum operate) {

        Map<RefundStatusEnum, RefundStepVO> flow;

        if (type.equals(RefuseTypeEnum.RETURN_MONEY) && paymentType.equals(PaymentTypeEnum.COD)) {
            //货到付款退款
            flow = COD_REFUND_FLOW;
        } else if (type.equals(RefuseTypeEnum.RETURN_MONEY) && paymentType.equals(PaymentTypeEnum.ONLINE)) {
            //款到发货退款
            flow = ONLINE_REFUND_FLOW;
        } else if (type.equals(RefuseTypeEnum.RETURN_GOODS) && paymentType.equals(PaymentTypeEnum.COD)) {
            //货到付款退货
            flow = COD_RETURN_FLOW;
        } else {
            //款到发货退货
            flow = ONLINE_RETURN_FLOW;
        }


        RefundStepVO step = flow.get(status);

        return step.checkAllowable(operate);

    }

}
