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
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.WithdrawCountClient;
import cloud.shopfly.b2c.core.distribution.service.WithdrawCountManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 可提现金额计算
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午7:46
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class WithdrawCountClientDefaultImpl implements WithdrawCountClient {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private WithdrawCountManager withdrawCountManager;

    /**
     * 每天执行结算
     */
    @Override
    public void everyDay() {
        withdrawCountManager.withdrawCount();
    }
}
