package cloud.shopfly.b2c.consumer.job.handler;

import cloud.shopfly.b2c.consumer.job.execute.EveryTenMinutesExecute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * nothing impl
 * @version v1.0
 * @Description: Perform scheduled tasks every 10 minutes
 * @Author: gy
 * @Date: 2020/6/10 0010 9:50
 */
//@Component
public class EveryTenMinutesExecuteJobHandler {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private List<EveryTenMinutesExecute> everyTenMinutesExecutes;

    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("EveryTenMinutes job start");
            }
            for (EveryTenMinutesExecute everyMonthExecute : everyTenMinutesExecutes) {
                everyMonthExecute.everyTenMinutes();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("EveryTenMinutes job end");
            }
        } catch (Exception e) {
            this.logger.error("Abnormal Monthly Tasks：", e);
        }
    }

}
