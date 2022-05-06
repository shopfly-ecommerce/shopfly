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
package cloud.shopfly.b2c.consumer.core.receiver;

import cloud.shopfly.b2c.consumer.core.event.MemberInfoChangeEvent;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
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
