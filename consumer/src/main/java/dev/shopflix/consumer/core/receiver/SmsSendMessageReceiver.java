/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.SmsSendMessageEvent;
import dev.shopflix.core.base.model.vo.SmsSendVO;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
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
 * 发送短信
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:32:15
 */
@Component
public class SmsSendMessageReceiver {

    @Autowired(required = false)
    private List<SmsSendMessageEvent> events;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 短信消息
     *
     * @param smsSendVO 短信vo
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.SEND_MESSAGE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.SEND_MESSAGE, type = ExchangeTypes.FANOUT)
    ))
    public void sendMessage(SmsSendVO smsSendVO) {
        if (events != null) {
            for (SmsSendMessageEvent event : events) {
                try {
                    event.send(smsSendVO);
                } catch (Exception e) {
                    logger.error("发送短信出错", e);
                }
            }
        }
    }
}
