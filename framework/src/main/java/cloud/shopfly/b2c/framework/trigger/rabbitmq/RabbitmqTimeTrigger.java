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

import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.trigger.rabbitmq.model.TimeTriggerMsg;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.trigger.util.RabbitmqTriggerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delayed task productionrabbitmqimplementation
 *
 * @author Chopper
 * @version v1.0
 * @Description: The principle of：usingamqpThe timeout attribute of the dead letter queue, which transfers the timed out task to the normal queue for the consumer to execute.<br/>
 * Add a task and identify the task execution、beanid、Execution time,hashThe value stored inredis, indicating that a task needs to be executed<p/>
 * Task edit, delete the previous identity, add the task again<br/>
 * Add delete, deleteredisThe task id is not available to the consumer at execution timeredis, the delay task will not be executed
 * <p>
 * 2019-02-01 In the afternoon4:01
 * @since v7.0
 */
@Component
public class RabbitmqTimeTrigger implements TimeTrigger {


    /**
     * The introduction ofrabbitOperation template of
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Cache cache;


    protected final Log logger = LogFactory.getLog(this.getClass());


    /**
     * Adding a Delayed Task
     *
     * @param executerName actuator
     * @param param        Perform parameter
     * @param triggerTime  The execution time
     * @param uniqueKey    If it is a need to have a modification/Cancel the delayed task of the delayed task function,<br/>
     *                     Please fill in this parameter for subsequent deletion and modification as the unique certificate<br/>
     *                     Recommended parameters are：PINTUAZN_{ACTIVITY_ID} For example,pintuan_123<br/>
     *                     Globally unique within a service
     */
    @Override
    public void add(String executerName, Object param, Long triggerTime, String uniqueKey) {

        if (StringUtil.isEmpty(uniqueKey)) {
            uniqueKey = StringUtil.getRandStr(10);
        }
        // Indicates that a task needs to be executed
        cache.put(RabbitmqTriggerUtil.generate(executerName, triggerTime, uniqueKey), 1);

        TimeTriggerMsg timeTriggerMsg = new TimeTriggerMsg(executerName, param, triggerTime, uniqueKey);
        if (logger.isDebugEnabled()) {
            logger.debug("Timed execution【" + DateUtil.toString(triggerTime, "yyyy-MM-dd HH:mm:ss") + "】Consumption,【" + param.toString() + "】");
        }
        rabbitTemplate.convertAndSend(TimeTriggerConfig.DELAYED_EXCHANGE_XDELAY, TimeTriggerConfig.DELAY_ROUTING_KEY_XDELAY, timeTriggerMsg, message -> {

            // If the delayed task is supposed to be executed before the current date, remedy this by requiring the system to execute it one second later
            if (triggerTime < DateUtil.getDateline()) {
                message.getMessageProperties().setDelay(1000);
            } else {
                message.getMessageProperties().setDelay((int) (triggerTime - DateUtil.getDateline()) * 1000);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("There are【" + message.getMessageProperties().getExpiration() + "】Perform a task");
            }

            return message;
        });
    }

    /**
     * Modifying a Delayed Task
     *
     * @param executerName actuator
     * @param param        Perform parameter
     * @param triggerTime  The execution time
     * @param uniqueKey    Unique credentials when adding tasks
     */
    @Override
    public void edit(String executerName, Object param, Long oldTriggerTime, Long triggerTime, String uniqueKey) {

        // Identification task abandonment
        cache.remove(RabbitmqTriggerUtil.generate(executerName, oldTriggerTime, uniqueKey));
        // Re-add a Task
        this.add(executerName, param, triggerTime, uniqueKey);
    }

    /**
     * Deleting a Delayed Task
     *
     * @param executerName actuator
     * @param triggerTime  The execution time
     * @param uniqueKey    Unique credentials when adding tasks
     */
    @Override
    public void delete(String executerName, Long triggerTime, String uniqueKey) {
        cache.remove(RabbitmqTriggerUtil.generate(executerName, triggerTime, uniqueKey));
    }


}
