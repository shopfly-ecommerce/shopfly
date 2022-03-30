/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.job.execute.EveryMonthExecute;
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
 * 每月执行调用
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-25 上午8:26
 */
@Component
public class EveryMonthExecuteReceiver {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired(required = false)
    private List<EveryMonthExecute> everyMonthExecutes;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = JobAmqpExchange.EVERY_MONTH_EXECUTE + "_QUEUE"),
            exchange = @Exchange(value = JobAmqpExchange.EVERY_MONTH_EXECUTE, type = ExchangeTypes.FANOUT)
    ))
    public void everyMonth() {
        if (everyMonthExecutes != null) {
            for (EveryMonthExecute everyMonthExecute : everyMonthExecutes) {
                try {
                    everyMonthExecute.everyMonth();
                } catch (Exception e) {
                    logger.error("每月任务异常：", e);
                }
            }
        }


    }


}
