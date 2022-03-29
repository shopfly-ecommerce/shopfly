package com.enation.app.javashop.consumer.core.receiver;

import cn.hutool.core.collection.CollUtil;
import com.enation.app.javashop.consumer.job.execute.EveryTenMinutesExecute;
import com.enation.app.javashop.core.base.JobAmqpExchange;
import com.enation.app.javashop.framework.logs.Logger;
import com.enation.app.javashop.framework.logs.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version v1.0
 * @Description: 监听每分钟的定时任务
 * @Author: gy
 * @Date: 2020/6/10 0010 9:59
 */
@Component
public class EveryTenMinutesExecuteReceiver {

    @Autowired(required = false)
    private List<EveryTenMinutesExecute> everyTenMinutesExecute;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = JobAmqpExchange.EVERY_TEN_MINUTES_EXECUTE + "_QUEUE"),
            exchange = @Exchange(value = JobAmqpExchange.EVERY_TEN_MINUTES_EXECUTE, type = ExchangeTypes.FANOUT)
    ))
    public void everyTenMinutes() {
        if (CollUtil.isNotEmpty(everyTenMinutesExecute)) {
            try {
                everyTenMinutesExecute.forEach(EveryTenMinutesExecute::everyTenMinutes);
            } catch (Exception e) {
                logger.error("每十分钟任务异常：", e);
            }

        }
    }


}
