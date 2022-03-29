/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.trigger.rabbitmq;

import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.trigger.Interface.TimeTrigger;
import com.enation.app.javashop.framework.trigger.rabbitmq.model.TimeTriggerMsg;
import com.enation.app.javashop.framework.trigger.util.RabbitmqTriggerUtil;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.StringUtil;
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