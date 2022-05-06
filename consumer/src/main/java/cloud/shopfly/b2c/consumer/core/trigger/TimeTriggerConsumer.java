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
 * 延时任务 消息消费者
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/12 下午4:52
 */
@Component
public class TimeTriggerConsumer {


    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private Cache cache;

    /**
     * 接收消息，监听 CONSUMPTION_QUEUE 队列
     */
    @RabbitListener(queues = TimeTriggerConfig.IMMEDIATE_QUEUE_XDELAY)
    public void consume(TimeTriggerMsg timeTriggerMsg) {

        try {
            String key = RabbitmqTriggerUtil.generate(timeTriggerMsg.getTriggerExecuter(), timeTriggerMsg.getTriggerTime(), timeTriggerMsg.getUniqueKey());
            //如果这个任务被标识不执行
            if (cache.get(key) == null) {

                if (logger.isDebugEnabled()) {
                    logger.debug("执行器执行被取消：" + timeTriggerMsg.getTriggerExecuter() + "|任务标识：" + timeTriggerMsg.getUniqueKey());
                }
                return;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("执行器执行：" + timeTriggerMsg.getTriggerExecuter());
                logger.debug("执行器参数：" + timeTriggerMsg.getParam().toString());
            }

            //执行任务前 清除标识
            cache.remove(key);

            TimeTriggerExecuter timeTriggerExecuter = (TimeTriggerExecuter) ApplicationContextHolder.getBean(timeTriggerMsg.getTriggerExecuter());
            timeTriggerExecuter.execute(timeTriggerMsg.getParam());

        } catch (Exception e) {
            logger.error("延时任务异常：", e);
        }
    }

}