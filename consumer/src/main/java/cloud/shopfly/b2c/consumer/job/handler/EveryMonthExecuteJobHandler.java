/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.consumer.job.handler;

import cloud.shopfly.b2c.consumer.job.execute.EveryMonthExecute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Perform daily
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-06 In the morning4:24
 */
@Component
public class EveryMonthExecuteJobHandler {


    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private List<EveryMonthExecute> everyMonthExecutes;

    @Scheduled(cron = "0 0 1 1 * ?")
    public void execute() throws Exception {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("EveryMonth job start");
            }
            for (EveryMonthExecute everyMonthExecute : everyMonthExecutes) {
                everyMonthExecute.everyMonth();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("EveryMonth job end");
            }
        } catch (Exception e) {
            this.logger.error("Monthly taskAMQPMessage sending exception：", e);
        }
    }
}
