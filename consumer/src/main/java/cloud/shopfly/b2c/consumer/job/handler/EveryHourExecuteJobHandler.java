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
package cloud.shopfly.b2c.consumer.job.handler;

import cloud.shopfly.b2c.consumer.job.execute.EveryHourExecute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Hourly execution
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-06 In the morning4:24
 */
@Component
public class EveryHourExecuteJobHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private List<EveryHourExecute> everyHourExecutes;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void execute() throws Exception {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("EveryHour job start");
            }
            for (EveryHourExecute everyHourExecute : everyHourExecutes) {
                everyHourExecute.everyHour();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("EveryHour job end");
            }
        } catch (Exception e) {
            this.logger.error("The hourly task is abnormal：", e);
        }
    }
}
