/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.consumer.job.handler;

import dev.shopflix.consumer.job.execute.EveryMonthExecute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 每日执行
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-06 上午4:24
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
            this.logger.error("每月任务AMQP消息发送异常：", e);
        }
    }
}
