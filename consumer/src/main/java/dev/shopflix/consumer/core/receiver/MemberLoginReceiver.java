/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.receiver;

import dev.shopflix.consumer.core.event.MemberLoginEvent;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.member.model.vo.MemberLoginMsg;
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
 * 会员登陆消费者
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月23日 上午10:31:06
 */
@Component
public class MemberLoginReceiver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired(required = false)
    private List<MemberLoginEvent> events;

    /**
     * 会员登陆
     *
     * @param memberLoginMsg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AmqpExchange.MEMEBER_LOGIN + "LOGIN_QUEUE"),
            exchange = @Exchange(value = AmqpExchange.MEMEBER_LOGIN, type = ExchangeTypes.FANOUT)
    ))
    public void memberLogin(MemberLoginMsg memberLoginMsg) {

        if (events != null) {
            for (MemberLoginEvent event : events) {
                try {
                    event.memberLogin(memberLoginMsg);
                } catch (Exception e) {
                    logger.error("会员登录出错", e);
                }
            }
        }
    }
}
