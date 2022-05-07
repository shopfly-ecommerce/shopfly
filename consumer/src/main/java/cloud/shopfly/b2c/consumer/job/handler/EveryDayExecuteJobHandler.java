/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.consumer.job.handler;

import cloud.shopfly.b2c.consumer.job.execute.EveryDayExecute;
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
public class EveryDayExecuteJobHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private List<EveryDayExecute> everyDayExecutes;

    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {

        try {
            this.logger.debug("every data execute");
            for (EveryDayExecute everyDayExecute : everyDayExecutes) {
                everyDayExecute.everyDay();
            }
            this.logger.debug("every data execute end");

        } catch (Exception e) {
            this.logger.error("Daily Task exception：", e);
        }
    }
}
