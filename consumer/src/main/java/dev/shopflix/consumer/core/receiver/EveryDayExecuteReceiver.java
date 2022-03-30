/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.job.execute.EveryDayExecute;
import dev.shopflix.core.base.JobAmqpExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 每日执行调用
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-25 上午8:26
 */
@Component
public class EveryDayExecuteReceiver {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private List<EveryDayExecute> everyDayExecutes;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = JobAmqpExchange.EVERY_DAY_EXECUTE + "_QUEUE"),
            exchange = @Exchange(value = JobAmqpExchange.EVERY_DAY_EXECUTE, type = ExchangeTypes.FANOUT)
    ))
    public void everyDay() {
        if (everyDayExecutes != null) {
            for (EveryDayExecute everyDayExecute : everyDayExecutes) {
                try {
                    everyDayExecute.everyDay();
                } catch (Exception e) {
                    logger.error("每日任务异常：", e);
                }
            }
        }
    }

}
