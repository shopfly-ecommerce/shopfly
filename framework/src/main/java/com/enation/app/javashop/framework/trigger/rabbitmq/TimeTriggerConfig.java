/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.trigger.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由配置=
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/12 下午3:25
 */

@Configuration
public class TimeTriggerConfig {

    /**
     * 队列枚举
     */
    public final static String IMMEDIATE_QUEUE_XDELAY = "IMMEDIATE_QUEUE_XDELAY";
    /**
     * 交换机
     */
    public final static String DELAYED_EXCHANGE_XDELAY = "DELAYED_EXCHANGE_XDELAY";
    /**
     * routing
     */
    public final static String DELAY_ROUTING_KEY_XDELAY = "DELAY_ROUTING_KEY_XDELAY";


    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_XDELAY, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue(IMMEDIATE_QUEUE_XDELAY, true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(delayExchange()).with(IMMEDIATE_QUEUE_XDELAY).noargs();
    }
}