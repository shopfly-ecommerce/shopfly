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
import cloud.shopfly.b2c.core.client.statistics.SyncopateTableClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分表任务每日执行
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-16 下午4:17
 */
@Component
public class SyncopateTableJob implements EveryDayExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private SyncopateTableClient syncopateTableClient;

    /**
     * 每年执行
     */
    @Override
    public void everyDay() {
        try {
            syncopateTableClient.everyDay();
        } catch (Exception e) {
            logger.error("分表业务执行异常：", e);
        }

    }
}
