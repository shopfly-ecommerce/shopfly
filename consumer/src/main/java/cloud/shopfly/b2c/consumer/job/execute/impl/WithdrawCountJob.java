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
package cloud.shopfly.b2c.consumer.job.execute.impl;

import cloud.shopfly.b2c.consumer.job.execute.EveryDayExecute;
import cloud.shopfly.b2c.core.client.distribution.WithdrawCountClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The withdrawal amount is calculated
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 In the morning7:46
 */
@Service
public class WithdrawCountJob implements EveryDayExecute {


    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private WithdrawCountClient withdrawCountClient;


    /**
     * Perform daily settlement
     */
    @Override
    public void everyDay() {
        try {
            withdrawCountClient.everyDay();
        } catch (Exception e) {
            logger.error("The unlock amount will be automatically added to the withdrawal amount anomaly every day：",e);
        }
    }
}
