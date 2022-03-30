/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.trigger;

import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.context.ApplicationContextHolder;
import com.enation.app.javashop.framework.trigger.Interface.TimeTriggerExecuter;
import com.enation.app.javashop.framework.trigger.rabbitmq.TimeTriggerConfig;
import com.enation.app.javashop.framework.trigger.rabbitmq.model.TimeTriggerMsg;
import com.enation.app.javashop.framework.trigger.util.RabbitmqTriggerUtil;
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