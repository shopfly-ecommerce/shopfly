/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.MemberInfoChangeEvent;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
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
 * 修改会员资料
 *
 * @author zh
 * @version v7.0
 * @date 18/12/26 下午4:17
 * @since v7.0
 */
@Component
public class MemberInfoChangeReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired(required = false)
    private List<MemberInfoChangeEvent> events;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.MEMBER_INFO_CHANGE + "_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.MEMBER_INFO_CHANGE, type = ExchangeTypes.FANOUT)
    ))
    public void createIndexPage(Integer memberId) {
        if (events != null) {
            for (MemberInfoChangeEvent event : events) {
                try {
                    event.memberInfoChange(memberId);
                } catch (Exception e) {
                    logger.error("处理会员资料变化消息出错", e);
                }
            }
        }

    }
}
