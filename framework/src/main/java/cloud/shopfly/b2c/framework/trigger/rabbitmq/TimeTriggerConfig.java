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
package cloud.shopfly.b2c.framework.trigger.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * The routing configuration=
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/12 In the afternoon3:25
 */

@Configuration
public class TimeTriggerConfig {

    /**
     * Queue enumeration
     */
    public final static String IMMEDIATE_QUEUE_XDELAY = "IMMEDIATE_QUEUE_XDELAY";
    /**
     * switches
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
