package dev.shopflix.consumer.job.handler;

import dev.shopflix.core.base.JobAmqpExchange;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @Description: 每十分钟执行定时任务
 * @Author: gy
 * @Date: 2020/6/10 0010 9:50
 */
@JobHandler("everyTenMinutesExecuteJobHandler")
@Component
public class EveryTenMinutesExecuteJobHandler extends IJobHandler {



    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            amqpTemplate.convertAndSend(JobAmqpExchange.EVERY_TEN_MINUTES_EXECUTE,
                    JobAmqpExchange.EVERY_TEN_MINUTES_EXECUTE + "_ROUTING",
                    "");
        } catch (Exception e) {
            this.logger.error("每分钟任务AMQP消息发送异常：", e);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}
