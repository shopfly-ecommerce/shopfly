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

import cloud.shopfly.b2c.consumer.job.execute.EveryMonthExecute;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The number of member login returns to zero
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-19 In the afternoon2:40
 */
@Component
public class MemberLoginNumToZeroJob implements EveryMonthExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MemberClient memberClient;

    /**
     * Perform a month
     */
    @Override
    public void everyMonth() {
        try {
            memberClient.loginNumToZero();
        } catch (Exception e) {
            this.logger.error("The login times of member return to zero abnormal：", e);
        }
    }
}
