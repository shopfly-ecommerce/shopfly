/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.job.handler;

import dev.shopflix.core.base.JobAmqpExchange;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每日执行
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-06 上午4:24
 */
@JobHandler(value = "everyYearExecuteJobHandler")
@Component
public class EveryYearExecuteJobHandler extends IJobHandler {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MessageSender messageSender;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            this.messageSender.send(new MqMessage(JobAmqpExchange.EVERY_YEAR_EXECUTE,
                    JobAmqpExchange.EVERY_YEAR_EXECUTE + "_ROUTING",
                    ""));
        } catch (Exception e) {
            this.logger.error("每年任务AMQP消息发送异常：", e);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}