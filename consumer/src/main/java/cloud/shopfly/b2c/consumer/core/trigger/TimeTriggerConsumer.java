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
package cloud.shopfly.b2c.consumer.core.trigger;

import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ApplicationContextHolder;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTriggerExecuter;
import cloud.shopfly.b2c.framework.trigger.rabbitmq.TimeTriggerConfig;
import cloud.shopfly.b2c.framework.trigger.rabbitmq.model.TimeTriggerMsg;
import cloud.shopfly.b2c.framework.trigger.util.RabbitmqTriggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delayed task message consumer
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/12 In the afternoon4:52
 */
@Component
public class TimeTriggerConsumer {


    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private Cache cache;

    /**
     * Receive messages, listenCONSUMPTION_QUEUE The queue
     */
    @RabbitListener(queues = TimeTriggerConfig.IMMEDIATE_QUEUE_XDELAY)
    public void consume(TimeTriggerMsg timeTriggerMsg) {

        try {
            String key = RabbitmqTriggerUtil.generate(timeTriggerMsg.getTriggerExecuter(), timeTriggerMsg.getTriggerTime(), timeTriggerMsg.getUniqueKey());
            //如果这个任务被标识不执行
            if (cache.get(key) == null) {

                if (logger.isDebugEnabled()) {
                    logger.debug(" time trigger executer cancel:" + timeTriggerMsg.getTriggerExecuter() + "UniqueKey is：" + timeTriggerMsg.getUniqueKey());
                }
                return;
            }
            if (logger.isDebugEnabled()) {
                logger.debug(" time trigger execute:：" + timeTriggerMsg.getTriggerExecuter());
                logger.debug("param is: ：" + timeTriggerMsg.getParam().toString());
            }

            // Clear identifiers before performing tasks
            cache.remove(key);

            TimeTriggerExecuter timeTriggerExecuter = (TimeTriggerExecuter) ApplicationContextHolder.getBean(timeTriggerMsg.getTriggerExecuter());
            timeTriggerExecuter.execute(timeTriggerMsg.getParam());

        } catch (Exception e) {
            logger.error("Delayed task exception：", e);
        }
    }

}
