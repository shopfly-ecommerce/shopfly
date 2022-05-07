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
package cloud.shopfly.b2c.core.trade.order.support;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderOperateEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Order operation inspection
 *
 * @author Snow create in 2018/5/16
 * @version 3.0
 * Process externalization, written by kingapex  in  2019/1/28
 * @since v7.0.0
 */
public class OrderOperateChecker {

    private Map<OrderStatusEnum, OrderStep> flow;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * The monitor must initialize the process
     *
     * @param flow
     */
    public OrderOperateChecker(Map<OrderStatusEnum, OrderStep> flow) {
        this.flow = flow;
    }

    /**
     * Verify whether the operation is allowed
     *
     * @param status
     * @param operate
     * @return
     */
    public boolean checkAllowable(OrderStatusEnum status, OrderOperateEnum operate) {

        if (flow == null) {
            logger.error("status[" + status + "] and operate[" + operate + "] Did not findflow,flowNull, force returnfalse");

            return false;
        }

        OrderStep step = flow.get(status);

        if (step == null) {

            logger.error("status[" + status + "] and operate[" + operate + "] Did not findstep,stepNull, force returnfalse");

            return false;
        }

        return step.checkAllowable(operate);

    }


}
