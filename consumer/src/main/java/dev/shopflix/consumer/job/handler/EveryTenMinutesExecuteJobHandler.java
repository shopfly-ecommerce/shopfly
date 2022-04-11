package dev.shopflix.consumer.job.handler;

import dev.shopflix.consumer.job.execute.EveryTenMinutesExecute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * nothing impl
 * @version v1.0
 * @Description: 每十分钟执行定时任务
 * @Author: gy
 * @Date: 2020/6/10 0010 9:50
 */
//@Component
public class EveryTenMinutesExecuteJobHandler {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private List<EveryTenMinutesExecute> everyTenMinutesExecutes;

    @Scheduled(cron = "0 */10 * * * ?")
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
            this.logger.error("每月任务异常：", e);
        }
    }

}
