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
 * Return operation test to see whether an operation is allowed in a certain state
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the morning11:20 2018/5/2
 */
public class RefundOperateChecker {

    /**
     * Cash on delivery refunds
     */
    private static final Map<RefundStatusEnum, RefundStepVO> COD_REFUND_FLOW = new HashMap<>();
    /**
     * Refund upon delivery
     */
    private static final Map<RefundStatusEnum, RefundStepVO> ONLINE_REFUND_FLOW = new HashMap<>();
    /**
     * Cash on delivery returns
     */
    private static final Map<RefundStatusEnum, RefundStepVO> COD_RETURN_FLOW = new HashMap<>();
    /**
     * Return the money upon delivery
     */
    private static final Map<RefundStatusEnum, RefundStepVO> ONLINE_RETURN_FLOW = new HashMap<>();

    /**
     * In the application state, the buyer can cancel, the seller can review
     */
    private static final RefundStepVO APPLY_STEP = new RefundStepVO(RefundStatusEnum.APPLY, RefundOperateEnum.CANCEL, RefundOperateEnum.ADMIN_APPROVAL);

    /**
     * Check the actions that can be performed in the rejected state
     */
    private static final RefundStepVO REFUSE_STEP = new RefundStepVO(RefundStatusEnum.REFUSE);

    /**
     * This operation can be performed when the application is cancelled
     */
    private static final RefundStepVO CANCEL_STEP = new RefundStepVO(RefundStatusEnum.CANCEL);

    /**
     * What can be done in the state of refund
     */
    private static final RefundStepVO REFUNDING_STEP = new RefundStepVO(RefundStatusEnum.REFUNDING);

//	/**
//	 * If a refund fails, the administrator can audit it
//	 */
//	private static final RefundStepVO REFUNDFAIL_STEP = new RefundStepVO(RefundStatusEnum.REFUNDFAIL,RefundOperateEnum.ADMIN_REFUND);

    /**
     * Operations that can be performed in the completed state
     */
    private static final RefundStepVO COMPLETED_STEP = new RefundStepVO(RefundStatusEnum.COMPLETED);

    static {
        initCodRefundflow();
        initOnlineRefundflow();
        initCodReturnflow();
        initOnlionReturnflow();
    }

    /**
     * Initialize the CASH on delivery refund process
     */
    private static void initCodRefundflow() {


        // If the application succeeds, the administrator can refund the money
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);


        // If the refund fails, the administrator can refund the money
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
     * Initialize the payment to shipment refund process
     */
    private static void initOnlineRefundflow() {


        // If the application succeeds, the administrator can refund the money
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        // If the refund fails, the administrator can refund the money
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
     * Initialize the cash on delivery return process
     */
    private static void initCodReturnflow() {

        // If the return application is approved, the administrator can return the goods to the warehouse
        RefundStepVO passStep = new RefundStepVO(RefundStatusEnum.PASS, RefundOperateEnum.STOCK_IN);

        // If manual processing or refund fails, the administrator can refund the money
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        // If the refund fails, the administrator can refund the money
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
     * Initialize the payment to shipment return process
     */
    private static void initOnlionReturnflow() {

        // When the return application is approved, the seller can return the goods to the warehouse
        RefundStepVO passStep = new RefundStepVO(RefundStatusEnum.PASS, RefundOperateEnum.STOCK_IN);

        // After manual processing, the administrator can refund the money
        RefundStepVO waitForManualStep = new RefundStepVO(RefundStatusEnum.WAIT_FOR_MANUAL, RefundOperateEnum.ADMIN_REFUND);

        // If the refund fails, the administrator can refund the money
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
     * Verify whether the operation is allowed
     *
     * @param type    Refund type
     * @param status  After state
     * @param operate Operation type
     * @return Whether operation is allowed
     */
    public static boolean checkAllowable(RefuseTypeEnum type, PaymentTypeEnum paymentType, RefundStatusEnum status, RefundOperateEnum operate) {

        Map<RefundStatusEnum, RefundStepVO> flow;

        if (type.equals(RefuseTypeEnum.RETURN_MONEY) && paymentType.equals(PaymentTypeEnum.COD)) {
            // Cash on delivery refunds
            flow = COD_REFUND_FLOW;
        } else if (type.equals(RefuseTypeEnum.RETURN_MONEY) && paymentType.equals(PaymentTypeEnum.ONLINE)) {
            // Refund upon delivery
            flow = ONLINE_REFUND_FLOW;
        } else if (type.equals(RefuseTypeEnum.RETURN_GOODS) && paymentType.equals(PaymentTypeEnum.COD)) {
            // Cash on delivery returns
            flow = COD_RETURN_FLOW;
        } else {
            // Return the money upon delivery
            flow = ONLINE_RETURN_FLOW;
        }


        RefundStepVO step = flow.get(status);

        return step.checkAllowable(operate);

    }

}
