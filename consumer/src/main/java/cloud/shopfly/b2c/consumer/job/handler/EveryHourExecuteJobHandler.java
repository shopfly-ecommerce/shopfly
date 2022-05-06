/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 每小时执行
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-06 上午4:24
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
            this.logger.error("每小时任务异常：", e);
        }
    }
}
