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
 * 延时任务生产 rabbitmq实现
 *
 * @author Chopper
 * @version v1.0
 * @Description: 原理：利用amqp的死信队列的超时属性，将超时的任务转到普通队列交给消费者执行。<br/>
 * 添加任务，将任务执行标识、beanid、执行时间，hash值存入redis，标识任务需要执行<p/>
 * 任务编辑，将之前的标识删除，重新添加任务<br/>
 * 添加删除，删除redis中的任务标识，消费者执行时获取不到 redis中的标识，则不会执行延时任务
 * <p>
 * 2019-02-01 下午4:01
 * @since v7.0
 */
@Component
public class RabbitmqTimeTrigger implements TimeTrigger {


    /**
     * 引入rabbit的操作模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Cache cache;


    protected final Log logger = LogFactory.getLog(this.getClass());


    /**
     * 添加延时任务
     *
     * @param executerName 执行器
     * @param param        执行参数
     * @param triggerTime  执行时间
     * @param uniqueKey    如果是一个 需要有 修改/取消 延时任务功能的延时任务，<br/>
     *                     请填写此参数，作为后续删除，修改做为唯一凭证 <br/>
     *                     建议参数为：PINTUAZN_{ACTIVITY_ID} 例如 pintuan_123<br/>
     *                     业务内全局唯一
     */
    @Override
    public void add(String executerName, Object param, Long triggerTime, String uniqueKey) {

        if (StringUtil.isEmpty(uniqueKey)) {
            uniqueKey = StringUtil.getRandStr(10);
        }
        //标识任务需要执行
        cache.put(RabbitmqTriggerUtil.generate(executerName, triggerTime, uniqueKey), 1);

        TimeTriggerMsg timeTriggerMsg = new TimeTriggerMsg(executerName, param, triggerTime, uniqueKey);
        if (logger.isDebugEnabled()) {
            logger.debug("定时执行在【" + DateUtil.toString(triggerTime, "yyyy-MM-dd HH:mm:ss") + "】，消费【" + param.toString() + "】");
        }
        rabbitTemplate.convertAndSend(TimeTriggerConfig.DELAYED_EXCHANGE_XDELAY, TimeTriggerConfig.DELAY_ROUTING_KEY_XDELAY, timeTriggerMsg, message -> {

            //如果执行的延时任务应该是在现在日期之前执行的，那么补救一下，要求系统一秒钟后执行
            if (triggerTime < DateUtil.getDateline()) {
                message.getMessageProperties().setDelay(1000);
            } else {
                message.getMessageProperties().setDelay((int) (triggerTime - DateUtil.getDateline()) * 1000);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("还有【" + message.getMessageProperties().getExpiration() + "】执行任务");
            }

            return message;
        });
    }

    /**
     * 修改延时任务
     *
     * @param executerName 执行器
     * @param param        执行参数
     * @param triggerTime  执行时间
     * @param uniqueKey    添加任务时的唯一凭证
     */
    @Override
    public void edit(String executerName, Object param, Long oldTriggerTime, Long triggerTime, String uniqueKey) {

        //标识任务放弃
        cache.remove(RabbitmqTriggerUtil.generate(executerName, oldTriggerTime, uniqueKey));
        //重新添加任务
        this.add(executerName, param, triggerTime, uniqueKey);
    }

    /**
     * 删除延时任务
     *
     * @param executerName 执行器
     * @param triggerTime  执行时间
     * @param uniqueKey    添加任务时的唯一凭证
     */
    @Override
    public void delete(String executerName, Long triggerTime, String uniqueKey) {
        cache.remove(RabbitmqTriggerUtil.generate(executerName, triggerTime, uniqueKey));
    }


}