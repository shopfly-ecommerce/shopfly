/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.consumer.job.handler;

import dev.shopflix.consumer.job.execute.EveryDayExecute;
import dev.shopflix.consumer.job.execute.EveryMinutesExecute;
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
            this.logger.error("每日任务异常：", e);
        }
    }
}
