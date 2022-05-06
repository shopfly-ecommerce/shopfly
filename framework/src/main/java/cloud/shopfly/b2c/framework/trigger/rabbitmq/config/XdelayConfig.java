/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.trigger.rabbitmq.config;

import cloud.shopfly.b2c.framework.trigger.rabbitmq.TimeTriggerConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Mq 延时队列配置
 * @author liushuai(liushuai711@gmail.com)
 * @version v1.0
 * @since v7.0
 * 2019/3/4 下午9:12
 * @Description:
 *
 */

@Configuration
public class XdelayConfig {

    /**
     * 创建一个立即消费队列
     * @return
     */
    @Bean
    public Queue immediateQueue() {
        return new Queue(TimeTriggerConfig.IMMEDIATE_QUEUE_XDELAY, true);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(TimeTriggerConfig.DELAYED_EXCHANGE_XDELAY, "x-delayed-message", true, false, args);
    }


    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(immediateQueue()).to(delayExchange()).with(TimeTriggerConfig.DELAY_ROUTING_KEY_XDELAY).noargs();
    }
}

