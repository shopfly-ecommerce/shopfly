/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

